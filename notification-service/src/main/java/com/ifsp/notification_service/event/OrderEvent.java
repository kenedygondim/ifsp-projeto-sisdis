package com.ifsp.notification_service.event;

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
    private String status;
    private BigDecimal valorTotal;
    private List<String> produtos;
}