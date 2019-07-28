package com.splitwise.splitwise.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AppExceptionHandler {

    //TODO
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public void  test() {

    }
}
