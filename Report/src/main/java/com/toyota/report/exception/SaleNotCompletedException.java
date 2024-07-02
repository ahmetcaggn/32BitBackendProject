package com.toyota.report.exception;

public class SaleNotCompletedException extends RuntimeException{
    public SaleNotCompletedException(String message) {
        super(message);
    }
}
