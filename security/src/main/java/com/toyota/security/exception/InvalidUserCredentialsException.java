package com.toyota.security.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException(String message){
        super(message);
    }
}
