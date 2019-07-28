package com.splitwise.splitwise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AppExceptionHandler {

    //TODO
    @ExceptionHandler(value = {SplitwiseAppException.class})
    public ResponseEntity<?> handleSericeException(final SplitwiseAppException e, final WebRequest webRequest) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
