package com.ifsp.order_service.event;

import com.ifsp.order_service.enumeration.OrderStatusEnum;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent implements Serializable {
    private Long orderId;
    private Long usuarioId;
    private String usuarioEmail;
    private OrderStatusEnum status;
    private BigDecimal valorTotal;
    private List<String> produtos; // Nomes dos produtos
}