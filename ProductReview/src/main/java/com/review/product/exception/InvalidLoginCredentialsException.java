package com.review.product.exception;

@SuppressWarnings("serial")
public class InvalidLoginCredentialsException extends RuntimeException{

	private String message;

	public InvalidLoginCredentialsException(String message) {
		super();
		this.message = message;
	}
	
	public InvalidLoginCredentialsException() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
