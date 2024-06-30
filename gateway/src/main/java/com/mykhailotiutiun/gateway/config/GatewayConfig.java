package com.mykhailotiutiun.gateway.config;

import com.mykhailotiutiun.gateway.filter.JwtAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthFilter filter;

    public GatewayConfig(JwtAuthFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                .route("product-service", r -> r.path("/products/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://product-service"))

                .route("order-service", r -> r.path("/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))

                .route("image-service", r -> r.path("/images/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://image-service"))

                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))

                .build();
    }
}
