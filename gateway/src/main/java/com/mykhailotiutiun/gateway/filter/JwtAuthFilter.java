package com.mykhailotiutiun.gateway.filter;

import com.mykhailotiutiun.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> unAuthEndpoints = List.of("/auth/login", "/auth/register");

        Predicate<ServerHttpRequest> isEndpointSecured = r -> unAuthEndpoints.stream().noneMatch(endpoint -> r.getURI().getPath().contains(endpoint));

        if(isEndpointSecured.test(request)){
            if(!request.getHeaders().containsKey("Authorization")) {
                return onError(exchange);
            }

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if (token != null && token.startsWith("Bearer ")){
                token = token.substring(7);
            }

            try {
                jwtUtil.validateToken(token);
            } catch (Exception e){
                return onError(exchange);
            }

        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}

