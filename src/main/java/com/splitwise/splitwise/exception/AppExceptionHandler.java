package com.splitwise.splitwise.exception;

import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AppExceptionHandler {

    //TODO
    @ExceptionHandler(value = {SplitwiseAppException.class})
    public ResponseEntity<?> handleSericeException(final SplitwiseAppException e, final WebRequest webRequest) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(e.getMessage(), new Date()));
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleInputValidationException(final MethodArgumentNotValidException ex,
            final WebRequest request) {
        final String message =
                ex.getBindingResult().getAllErrors()
                        .stream()
                        .map(objectError -> objectError.getDefaultMessage())
                        .collect(Collectors.joining(", " ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(message, new Date()));
    }


}
