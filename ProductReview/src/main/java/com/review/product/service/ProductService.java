package com.review.product.service;

import com.review.product.model.Product;

public interface ProductService {
	
	public Product addProduct(Product product);
	
	public boolean deleteProduct(int productID);
}
