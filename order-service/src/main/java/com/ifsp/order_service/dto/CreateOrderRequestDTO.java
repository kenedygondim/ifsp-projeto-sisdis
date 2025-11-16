package com.ifsp.order_service.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDTO {
    private Long usuarioId;
    private List<Long> produtoIds;
}