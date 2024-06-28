package com.toyota.Sales.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (methodKey.contains("getProductById")) {
            String result;
            if (response.status() == 404) {
                byte[] bodyData;
                try {
                    bodyData = response.body().asInputStream().readAllBytes();
                    result = new String(bodyData, StandardCharsets.UTF_8);
                    return new ProductNotFoundException(result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return new Exception(response.body().toString());
    }
}

