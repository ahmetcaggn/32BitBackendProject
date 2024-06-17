package com.toyota.Gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    private final RestTemplate restTemplate;

    public CustomAuthFilter(RestTemplate restTemplate) {
        super(Config.class);
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return this.onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

//
            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//
            if (!authHeader.startsWith("Bearer ")) {
                return this.onError(exchange, "Authorization header must start with Bearer", HttpStatus.UNAUTHORIZED);
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", authHeader);
            HttpEntity<Boolean> httpRequest = new HttpEntity<>(httpHeaders);
            String token = authHeader.substring(7);
            String urlValidate = "http://localhost:8400/security/validateToken?token="+token;

            if (!restTemplate.postForEntity(urlValidate,httpRequest,Boolean.class).getBody()) {
                return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
            }

            String path = exchange.getRequest().getPath().value();
            String serviceDeterminer = path.split("/")[1];
            String url = "http://localhost:8400/security/" + serviceDeterminer;

            Boolean isAuthorized = false;

            isAuthorized = restTemplate.exchange(url,HttpMethod.GET,httpRequest,Boolean.class).getBody();
            if (Boolean.TRUE.equals(isAuthorized)){
                return chain.filter(exchange);
            }else {
                return this.onError(exchange, "UnAuthrozied", HttpStatus.BAD_REQUEST);
            }
        }, 1);
    }


    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
//        response.getHeaders().add("error", err);
        return response.setComplete();
    }
}
