package com.toyota.Sales.exception;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(String message) {
        super(message);
    }
}
