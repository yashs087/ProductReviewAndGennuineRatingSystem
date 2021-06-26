package com.review.product.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//POJO class for Product
@Entity
@Table(name="product")
public class Product {
	
	@Id
	private int productID;
	private String productName;
	private String productCategory;
	private double productPrice;
	private String productDescription;
	
	public Product() {
		
	}
	
	public Product(int productID, String productName, String productCategory, double productPrice,
			String productDescription) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
}
