package com.review.product.exception;

public class InvalidLoginCredentialsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
