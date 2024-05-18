package com.toyota.Gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()

                .route(r -> r
                        .path("/sales/**")
                        .uri("lb://SALES")
                )
                .route(r-> r
                        .path("/campaigns/**")
                        .uri("lb://SALES")
                )
                .route(r -> r
                        .path("/product/**")
                        .uri("lb://PRODUCT")
                )
                .route(r-> r
                        .path("/employee/**")
                        .uri("lb://USERMANAGEMENT")
                )
                .route(r-> r
                        .path("/role/**")
                        .uri("lb://USERMANAGEMENT")
                )
                .route(r-> r
                        .path("/report/**")
                        .uri("lb://REPORT")
                )
                .build();
    }
}
