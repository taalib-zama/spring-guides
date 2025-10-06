package com.learning.core.JPADemo.spring_data_jpa_demo.exception;

import com.learning.core.JPADemo.spring_data_jpa_demo.SpringDataJpaDemoApplication;
import com.learning.core.JPADemo.spring_data_jpa_demo.entity.ErrorCodeMapping;
import com.learning.core.JPADemo.spring_data_jpa_demo.service.ErrorCodeMappingService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {


    private final ErrorCodeMappingService errorCodeMappingService;

    public GlobalExceptionsHandler(ErrorCodeMappingService errorCodeMappingService) {
        this.errorCodeMappingService = errorCodeMappingService;
    }


    // Add this for your custom exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("TodoNotFoundException: ", ex);

        ErrorCodeMapping errorCodeMapping = errorCodeMappingService.getOrCreate(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                errorCodeMapping.getId().toString(),
                errorCodeMapping.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //suggest some more general exception to be handled here



    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception: ", ex);

        ErrorCodeMapping errorCodeMapping = errorCodeMappingService.getOrCreate(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(errorCodeMapping.getId().toString(), errorCodeMapping.getMessage(), status);

        return new ResponseEntity<>(errorResponse, headers, status);
    }


    // Add this for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("General Exception: ", ex);

        ErrorCodeMapping errorCodeMapping = errorCodeMappingService.getOrCreate(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                errorCodeMapping.getId().toString(),
                errorCodeMapping.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }




    /*@ExceptionHandler(SpringDataJpaDemoApplication.class)
    public ResponseEntity<ErrorResponse> handleTodoNotFound(TodoNotFoundException e) {
        com.learning.core.JPADemo.spring_data_jpa_demo.exception.ErrorResponse error = new ErrorResponse("NOT_FOUND", e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }*/



    public Long getErrorCode(String message) {
        ErrorCodeMapping errorCodeMapping = errorCodeMappingService.getOrCreate(message);
        return errorCodeMapping.getId();
    }


   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, WebRequest request) {
        log.error("Unhandled exception: ", ex);
        Long errorCode = getErrorCode(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred. Error Code: " + errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

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

