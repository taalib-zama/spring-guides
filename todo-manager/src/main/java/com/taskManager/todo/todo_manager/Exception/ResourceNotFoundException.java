package com.taskManager.todo.todo_manager.Exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
    }
}
