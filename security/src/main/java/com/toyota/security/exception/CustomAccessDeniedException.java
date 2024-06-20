package com.toyota.security.exception;

public class CustomAccessDeniedException extends RuntimeException{
    public CustomAccessDeniedException(String message){
        super(message);
    }
}
