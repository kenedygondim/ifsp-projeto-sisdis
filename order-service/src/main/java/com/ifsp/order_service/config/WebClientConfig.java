package com.ifsp.order_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${restaurant.service.url}")
    private String restaurantServiceUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(restaurantServiceUrl)
                .build();
    }
}