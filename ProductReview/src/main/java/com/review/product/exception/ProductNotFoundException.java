package com.review.product.exception;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException{
	
	private String message;

	
	public ProductNotFoundException() {
		
	}
	
	public ProductNotFoundException(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
