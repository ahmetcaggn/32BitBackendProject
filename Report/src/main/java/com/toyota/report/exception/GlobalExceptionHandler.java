package com.toyota.report.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(SaleNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(SaleNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Object> handleProductNotFoundException(MismatchedInputException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleProductNotFoundException(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SaleNotCompletedException.class)
    public ResponseEntity<Object> handleSaleNotCompletedException(SaleNotCompletedException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(exception.getMessage());
    }

}
