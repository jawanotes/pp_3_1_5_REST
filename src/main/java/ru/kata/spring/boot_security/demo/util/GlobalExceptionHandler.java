package ru.kata.spring.boot_security.demo.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(IdNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
