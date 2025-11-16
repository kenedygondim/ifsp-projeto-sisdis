package com.ifsp.restaurant_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    private String nome;
    private String endereco;
}