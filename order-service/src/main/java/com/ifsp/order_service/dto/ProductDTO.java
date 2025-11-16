package com.ifsp.order_service.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private String categoria;
    private Long restauranteId;
}