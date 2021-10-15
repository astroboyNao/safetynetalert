package com.safetynet.alerts.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationHandler {
	
	@ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ExistException existException){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.CONFLICT.name()).msg(existException.getMessage()).build();
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException notFoundException){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.name()).msg(notFoundException.getMessage()).build();
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }
	
}
