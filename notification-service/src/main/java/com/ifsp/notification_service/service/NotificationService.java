package com.ifsp.notification_service.service;

import com.ifsp.notification_service.entity.Notification;
import com.ifsp.notification_service.enumeration.NotificationStatusEnum;
import com.ifsp.notification_service.event.OrderEvent;
import com.ifsp.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Transactional
    public void processOrderEvent(OrderEvent event) {
        log.info("Processing order event: {}", event);

        // Junta produtos com ponto e vírgula
        String produtosStr = String.join("; ", event.getProdutos());

        // Cria registro de notificação
        Notification notification = Notification.builder()
                .orderId(event.getOrderId())
                .usuarioId(event.getUsuarioId())
                .usuarioEmail(event.getUsuarioEmail())
                .orderStatus(event.getStatus())
                .valorTotal(event.getValorTotal())
                .produtos(produtosStr)
                .status(NotificationStatusEnum.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        try {
            // Monta corpo do email
            String emailBody = buildEmailBody(
                    event.getUsuarioEmail(),
                    produtosStr,
                    event.getStatus(),
                    event.getValorTotal().toString()
            );

            // Envia email
            emailService.sendEmail(
                    event.getUsuarioEmail(),
                    "Delivery - Atualização do Pedido #" + event.getOrderId(),
                    emailBody
            );

            notification.setStatus(NotificationStatusEnum.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {
            log.error("Failed to send notification for order: {}", event.getOrderId(), e);
            notification.setStatus(NotificationStatusEnum.FAILED);
            notification.setErrorMessage(e.getMessage());
        }

        notificationRepository.save(notification);
    }

    private String buildEmailBody(String email, String produtos, String status, String valorTotal) {
        return String.format("""
                Olá!
                
                Seu pedido foi atualizado.
                
                Detalhes do Pedido:
                - Email: %s
                - Produtos: %s
                - Status: %s
                - Valor Total: R$ %s
                
                Obrigado por usar nosso serviço!
                
                Equipe Delivery
                """, email, produtos, status, valorTotal);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByOrder(Long orderId) {
        return notificationRepository.findByOrderId(orderId);
    }
}
