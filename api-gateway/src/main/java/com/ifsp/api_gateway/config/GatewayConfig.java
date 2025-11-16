package com.ifsp.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("restaurant-service", r -> r
                        .path("/api/restaurants/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8081"))
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8081"))
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8082"))
                .route("order-service", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8082"))
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8083"))

                .build();
    }
}