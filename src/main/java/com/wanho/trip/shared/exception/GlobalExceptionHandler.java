package com.wanho.trip.shared.exception;


import com.wanho.trip.shared.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponse<?> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String objectName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(objectName, errorMessage != null ? errorMessage : "Invalid Input");
        });

        return APIResponse.failed(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(InvalidInputException.class)
    public APIResponse<?> InvalidInputException(InvalidInputException ex) {
        Map<String, String> errors = new HashMap<>();
        String errorMessage = ex.getMessage();
        errors.put(ex.getFiledName(), errorMessage != null ? errorMessage : "Invalid Input");
        return APIResponse.failed(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public APIResponse<?> BadCredentialsException(BadCredentialsException ex) {
        Map<String, String> errors = new HashMap<>();
        String errorMessage = ex.getMessage();
        errors.put("login failed", "Incorrect email or password");
        return APIResponse.failed(HttpStatus.UNAUTHORIZED, errors);
    }

    @ExceptionHandler(Exception.class)
    public APIResponse<?> defaultException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        String errorMessage = ex.getMessage();
        errors.put("unknown error", errorMessage != null ? errorMessage : "Invalid Input");

        return APIResponse.failed(HttpStatus.BAD_REQUEST, errors);
    }
}
