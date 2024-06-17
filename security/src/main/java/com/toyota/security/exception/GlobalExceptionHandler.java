package com.toyota.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<Object> handleProductNotFoundException(MethodArgumentTypeMismatchException exception) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }

}
