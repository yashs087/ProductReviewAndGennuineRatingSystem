package com.review.product.exception;

@SuppressWarnings("serial")
public class ReviewNotFoundException extends RuntimeException{

	private String message;

	public ReviewNotFoundException(String message) {
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
