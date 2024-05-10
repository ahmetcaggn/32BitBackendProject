package com.toyota.Sales.exception;

public class MissingRequestException extends RuntimeException{
    public MissingRequestException(String message){
        super(message);
    }
}
