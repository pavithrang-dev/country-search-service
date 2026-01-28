package com.example.country_search_service.exception;



public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException(String message) {
        super(message);
    }
}

