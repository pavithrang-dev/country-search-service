package com.example.country_search_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParam(
            MissingServletRequestParameterException ex) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Missing required query parameter: " + ex.getParameterName()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(
            MethodArgumentNotValidException ex) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ExceptionHandler(InvalidQueryException.class)
    public ResponseEntity<?> handleInvalidQuery(
            InvalidQueryException ex) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );
    }

    private ResponseEntity<Map<String, Object>> buildError(
            HttpStatus status,
            String message) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);

        return ResponseEntity.status(status).body(error);
    }
}

