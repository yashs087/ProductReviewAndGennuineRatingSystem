package com.review.product.service;

import java.util.List;

import com.review.product.model.Review;

public interface ReviewService {
	
	public List<Review> getAllReview();
	
	public Review addReview(String productName,Review review,int customerID);
	
	public List<Review> getReview(String productName);
	
}
