package com.example.Sales.exception;

public class SaleProductNotFoundException extends RuntimeException{
    public SaleProductNotFoundException(String message){
        super(message);
    }
}
