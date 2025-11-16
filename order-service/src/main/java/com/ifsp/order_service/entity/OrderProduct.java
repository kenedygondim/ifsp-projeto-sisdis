package com.ifsp.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    private String produtoNome;

    @Column(nullable = false)
    private java.math.BigDecimal produtoValor;
}