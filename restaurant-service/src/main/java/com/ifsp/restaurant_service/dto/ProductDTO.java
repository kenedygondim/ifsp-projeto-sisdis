package com.ifsp.restaurant_service.dto;

import com.ifsp.restaurant_service.enumeration.ProductCategoryEnum;
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
    private ProductCategoryEnum categoria;
    private Long restauranteId;
}