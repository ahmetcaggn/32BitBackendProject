package com.toyota.Gateway.exception;

public class NoSuchServiceException extends RuntimeException{
    public NoSuchServiceException(String message) {
        super(message);
    }
}
