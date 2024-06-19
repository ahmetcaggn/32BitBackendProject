package com.toyota.Gateway.filter;

import com.toyota.Gateway.dto.TokenValidateDto;
import com.toyota.Gateway.exception.CustomForbiddenException;
import com.toyota.Gateway.exception.InvalidJwtTokenException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


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
                throw new InvalidJwtTokenException("No authorization header");
            }

            String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

            if (!authHeader.startsWith("Bearer ")) {
                throw new InvalidJwtTokenException("Authorization header must start with Bearer");
            }


            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", authHeader);
            String token = authHeader.substring(7);
            HttpEntity<TokenValidateDto> httpRequest = new HttpEntity<>(new TokenValidateDto(token),httpHeaders);
            String urlValidate = "http://localhost:8400/security/validateToken";

            try {
                restTemplate.exchange(urlValidate,HttpMethod.POST, httpRequest, Boolean.class);
            } catch (Exception e) {
                throw new InvalidJwtTokenException("Invalid JWT token");
            }

            String path = exchange.getRequest().getPath().value();
            String serviceDeterminer = path.split("/")[1];
            HttpEntity<Boolean> httpRequestGet = new HttpEntity<>(httpHeaders);
            String url = "http://localhost:8400/security/" + serviceDeterminer;

            try {
                restTemplate.exchange(url, HttpMethod.GET, httpRequestGet, Boolean.class);
                return chain.filter(exchange);
            } catch (Exception e) {
                throw new CustomForbiddenException("Access denied!");
            }
        }, 1);
    }


    public static class Config {
    }
}