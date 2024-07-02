package com.toyota.Sales.exception;

public class PaymentMismatchException extends RuntimeException{
    public PaymentMismatchException(String message) {
        super(message);
    }
}
