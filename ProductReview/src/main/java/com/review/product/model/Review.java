package com.review.product.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


//POJO class for Review
@Entity
@Table(name="review")
public class Review {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int reviewID;
	
	@ManyToOne
	@JoinColumn(name="productID")
	private Product productID;
	@OneToOne
	@JoinColumn(name="customerID")
	private Customer customer;
	private int productRating;
	private String productReview;
	
	public Review() {
		
	}

	public Review(int reviewID,Product productID,Customer customer ,int productRating, String productReview) {
		super();
		this.reviewID = reviewID;
		this.productID = productID;
		this.customer = customer;
		this.productRating = productRating;
		this.productReview = productReview;
	}

	public int getReviewID() {
		return reviewID;
	}

	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}

	public Product getProductID() {
		return productID;
	}

	public void setProductID(Product productID) {
		this.productID = productID;
	}

	public int getProductRating() {
		return productRating;
	}

	public void setProductRating(int productRating) {
		this.productRating = productRating;
	}

	public String getProductReview() {
		return productReview;
	}

	public void setProductReview(String productReview) {
		this.productReview = productReview;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
