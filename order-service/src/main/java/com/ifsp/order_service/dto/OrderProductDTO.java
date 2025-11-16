package com.ifsp.order_service.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductDTO {
    private Long produtoId;
    private String produtoNome;
    private BigDecimal produtoValor;
}