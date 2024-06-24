package com.toyota.Sales.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(SaleNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(SaleNotFoundException exception) {
        log.error("Sale not found: ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleSaleProductNotFoundException(ProductNotFoundException exception) {
        log.error("Product not found: ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MissingRequestException.class)
    public ResponseEntity<Object> handleMissingRequestException(MissingRequestException exception) {
        log.error("Missing request: ", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<Object> handleCampaignNotFoundException(CampaignNotFoundException exception) {
        log.error("Campaign not found: ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SaleProductNotFoundException.class)
    public ResponseEntity<Object> handleSaleProductNotFoundException(SaleProductNotFoundException exception) {
        log.error("Sale product not found: ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> handleInvalidPropertyReferenceException(PropertyReferenceException exception) {
        log.error("Invalid property reference: ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Invalid property reference: " + exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> HandleFeignClientResponseException(MethodArgumentTypeMismatchException exception) {
        log.error("Method argument type mismatch: ", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
