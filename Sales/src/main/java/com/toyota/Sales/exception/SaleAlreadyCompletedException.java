package com.toyota.Sales.exception;

public class SaleAlreadyCompletedException extends RuntimeException{
    public SaleAlreadyCompletedException(String message) {
        super(message);
    }
}
