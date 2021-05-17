package com.review.product.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleException(ProductNotFoundException ex,WebRequest request) throws Exception{
		ExceptionResponse response = new ExceptionResponse(new Date(System.currentTimeMillis()),ex.getMessage(),request.getContextPath(),HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidLoginCredentialsException.class)
	public ResponseEntity<Object> handleLoginException(InvalidLoginCredentialsException ex,WebRequest request) throws Exception{
		ExceptionResponse response = new ExceptionResponse(new Date(System.currentTimeMillis()),ex.getMessage(),request.getContextPath(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> handleCustomerException(CustomerNotFoundException ex,WebRequest request) throws Exception{
		ExceptionResponse response = new ExceptionResponse(new Date(System.currentTimeMillis()),ex.getMessage(),request.getContextPath(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<Object> handleReviewException(ReviewNotFoundException ex,WebRequest request) throws Exception{
		ExceptionResponse response = new ExceptionResponse(new Date(System.currentTimeMillis()),ex.getMessage(),request.getContextPath(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
