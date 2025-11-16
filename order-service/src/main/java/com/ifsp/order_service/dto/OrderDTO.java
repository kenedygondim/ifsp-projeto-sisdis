package com.ifsp.order_service.dto;

import com.ifsp.order_service.enumeration.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long usuarioId;
    private OrderStatusEnum status;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;
    private List<OrderProductDTO> produtos;
}