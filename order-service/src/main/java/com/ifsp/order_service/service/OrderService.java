package com.ifsp.order_service.service;


import com.ifsp.order_service.config.RabbitMQConfig;
import com.ifsp.order_service.dto.CreateOrderRequestDTO;
import com.ifsp.order_service.dto.OrderDTO;
import com.ifsp.order_service.dto.ProductDTO;
import com.ifsp.order_service.entity.Order;
import com.ifsp.order_service.entity.OrderProduct;
import com.ifsp.order_service.entity.User;
import com.ifsp.order_service.enumeration.OrderStatusEnum;
import com.ifsp.order_service.event.OrderEvent;
import com.ifsp.order_service.repository.OrderRepository;
import com.ifsp.order_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.ifsp.order_service.dto.OrderProductDTO;
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WebClient webClient;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public OrderDTO createOrder(CreateOrderRequestDTO request) {
        User user = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ProductDTO> products = request.getProdutoIds().stream()
                .map(this::getProductFromRestaurantService)
                .collect(Collectors.toList());

        BigDecimal valorTotal = products.stream()
                .map(ProductDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .usuarioId(user.getId())
                .status(OrderStatusEnum.CRIADO)
                .valorTotal(valorTotal)
                .dataCriacao(LocalDateTime.now())
                .build();

        products.forEach(p -> {
            OrderProduct op = OrderProduct.builder()
                    .produtoId(p.getId())
                    .produtoNome(p.getNome())
                    .produtoValor(p.getValor())
                    .build();
            order.addProduct(op);
        });

        Order saved = orderRepository.save(order);

        publishOrderEvent(saved, user);

        return toDTO(saved);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatusEnum newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);

        // Busca usuário e publica evento
        User user = userRepository.findById(order.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        publishOrderEvent(updated, user);

        return toDTO(updated);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toDTO(order);
    }

    private ProductDTO getProductFromRestaurantService(Long productId) {
        return webClient.get()
                .uri("/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();
    }

    private void publishOrderEvent(Order order, User user) {
        List<String> productNames = order.getProdutos().stream()
                .map(OrderProduct::getProdutoNome)
                .collect(Collectors.toList());

        OrderEvent event = OrderEvent.builder()
                .orderId(order.getId())
                .usuarioId(user.getId())
                .usuarioEmail(user.getEmail())
                .status(order.getStatus())
                .valorTotal(order.getValorTotal())
                .produtos(productNames)
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                event
        );
    }

    private OrderDTO toDTO(Order order) {
        List<OrderProductDTO> products = order.getProdutos().stream()
                .map(op -> OrderProductDTO.builder()
                        .produtoId(op.getProdutoId())
                        .produtoNome(op.getProdutoNome())
                        .produtoValor(op.getProdutoValor())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .usuarioId(order.getUsuarioId())
                .status(order.getStatus())
                .valorTotal(order.getValorTotal())
                .dataCriacao(order.getDataCriacao())
                .produtos(products)
                .build();
    }
}