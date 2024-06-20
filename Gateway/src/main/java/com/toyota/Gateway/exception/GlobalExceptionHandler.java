package com.toyota.Gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException exception) {
        if (exception.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(exception.getMessage());
        }
        if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(exception.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unknown Server Error");
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Object> handleInvalidJwtTokenException(InvalidJwtTokenException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<Object> handleCustomForbiddenException(CustomForbiddenException exception){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }
}
