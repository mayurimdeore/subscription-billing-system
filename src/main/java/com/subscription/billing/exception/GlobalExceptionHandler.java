package com.subscription.billing.exception;

import java.time.LocalDateTime;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
	        MethodArgumentNotValidException ex,
	        ServletWebRequest request) {

	 

	    List<String> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error -> error.getField() + ": " + error.getDefaultMessage())
	            .collect(Collectors.toList());

	    ErrorResponse errorResponse = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            "Validation failed",
	            request.getRequest().getRequestURI(),
	            errors
	    );
	    

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(errorResponse);
	
	    
	    
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
	        ResourceNotFoundException ex,
	        ServletWebRequest request) {

	    ErrorResponse errorResponse = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            ex.getMessage(),
	            request.getRequest().getRequestURI(),
	            null
	    );

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(errorResponse);
	}
	
	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRuleException(
	        BusinessRuleException ex,
	        ServletWebRequest request) {

	    ErrorResponse errorResponse = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            ex.getMessage(),
	            request.getRequest().getRequestURI(),
	            null
	    );

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(errorResponse);
	}
}