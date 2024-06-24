package com.toyota.security.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<?> handleInvalidUserCredentialsException(InvalidUserCredentialsException exception) {
        log.error("Error occurred while validating User Credentials", exception);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<?> handleInvalidJwtException(InvalidJwtException exception) {
        log.error("Error occurred while validating JWT", exception);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(CustomAccessDeniedException exception) {
        log.error("Error occurred Access Denied", exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(CustomAuthenticationException exception) {
        log.error("Error occurred while authenticating", exception);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        log.error("Error occurred while fetching employee", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}
