package com.toyota.security.exception;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String message){
        super(message);
    }
}
