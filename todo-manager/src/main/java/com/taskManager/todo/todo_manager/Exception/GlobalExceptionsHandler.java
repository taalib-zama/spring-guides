package com.taskManager.todo.todo_manager.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTodoNotFound(TodoNotFoundException e) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e) {
        ErrorResponse error = new ErrorResponse("INTERNAL_ERROR", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}


/*
What it does:

Centralized error handling for entire application

@RestControllerAdvice = Global controller advice

@ExceptionHandler = Catches specific exceptions

Benefits After:
Clean Controllers - No try-catch blocks

Consistent Errors - Same format everywhere

Single Responsibility - Controllers focus on HTTP, handler focuses on errors

Maintainable - Change error format in one place

Result:
Before: Scattered error handling, inconsistent responses

After: Clean separation, consistent API responses, easier maintenance
 */

