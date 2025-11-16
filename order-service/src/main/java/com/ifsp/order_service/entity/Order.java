package com.ifsp.order_service.entity;

import com.ifsp.order_service.enumeration.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusEnum status;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderProduct> produtos = new ArrayList<>();

    public void addProduct(OrderProduct product) {
        produtos.add(product);
        product.setOrder(this);
    }
}