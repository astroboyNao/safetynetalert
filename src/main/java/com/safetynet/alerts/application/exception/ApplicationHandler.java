package com.safetynet.alerts.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The Class ApplicationHandler.
 */
@ControllerAdvice
public class ApplicationHandler {

	/**
	 * Handle exception.
	 *
	 * @param existException the exist exception
	 * @return the response entity
	 */
	@ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ExistException existException){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.CONFLICT.name()).msg(existException.getMessage()).build();
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

	/**
	 * Handle exception.
	 *
	 * @param notFoundException the not found exception
	 * @return the response entity
	 */
	@ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException notFoundException){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.name()).msg(notFoundException.getMessage()).build();
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

}
