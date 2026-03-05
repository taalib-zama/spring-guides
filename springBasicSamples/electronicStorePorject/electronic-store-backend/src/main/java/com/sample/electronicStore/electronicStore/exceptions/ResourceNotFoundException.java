package com.sample.electronicStore.electronicStore.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
    }
}
