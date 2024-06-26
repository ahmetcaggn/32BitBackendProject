package com.toyota.Gateway.config;

import com.toyota.Gateway.filter.CustomAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder, CustomAuthFilter authFilter) {

        return routeLocatorBuilder.routes()

                .route(r -> r
                        .path("/sales/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new CustomAuthFilter.Config()))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                )
                        )

                        .uri("lb://SALES")
                )
                .route(r -> r
                        .path("/campaigns/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new CustomAuthFilter.Config()))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                ))
                        .uri("lb://SALES")
                )
                .route(r -> r
                        .path("/product/**")
                        .filters(f -> f
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                )
                        )
                        .uri("lb://PRODUCT")
                )
                .route(r -> r
                        .path("/employee/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new CustomAuthFilter.Config()))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                )
                        )
                        .uri("lb://USERMANAGEMENT")
                )
                .route(r -> r
                        .path("/report/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new CustomAuthFilter.Config()))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                )
                        )
                        .uri("lb://REPORT")
                )
                .route(r -> r
                        .path("/security/**")
                        .filters(f -> f
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                )
                        )
                        .uri("lb://SECURITY")
                )
                .build();
    }
}
