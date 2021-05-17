package com.review.product.service;

import java.util.List;

import com.review.product.model.Customer;

public interface CustomerService {
	
	public Customer signUp(Customer customer);
	
	public Boolean validateLogin(Customer customer);
	
	public List<Customer> getAllCustomer();
}
