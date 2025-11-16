package com.ifsp.notification_service.entity;

import com.ifsp.notification_service.enumeration.NotificationStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String usuarioEmail;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(columnDefinition = "TEXT")
    private String produtos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatusEnum status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}