package com.ifsp.restaurant_service.entity;

import com.ifsp.restaurant_service.enumeration.ProductCategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategoryEnum categoria;

    @Column(nullable = false)
    private Long restauranteId;
}