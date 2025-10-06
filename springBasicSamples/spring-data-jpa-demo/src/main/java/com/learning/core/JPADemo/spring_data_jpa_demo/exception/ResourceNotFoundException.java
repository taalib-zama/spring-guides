package com.learning.core.JPADemo.spring_data_jpa_demo.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
    }
}
