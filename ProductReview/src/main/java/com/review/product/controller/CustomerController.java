package com.review.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.review.product.exception.CustomerNotFoundException;
import com.review.product.exception.InvalidLoginCredentialsException;
import com.review.product.exception.ProductNotFoundException;
import com.review.product.exception.ReviewNotFoundException;
import com.review.product.model.Customer;
import com.review.product.model.Product;
import com.review.product.model.Review;
import com.review.product.service.CustomerServiceImpl;
import com.review.product.service.ProductServiceImpl;
import com.review.product.service.ReviewServiceImpl;

//Controller class for Customer
@RestController
@RequestMapping("/Customer")
public class CustomerController {
	
	private Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	@Autowired
	private ProductServiceImpl productServiceImpl;
	@Autowired
	private ReviewServiceImpl reviewServiceImpl;
	
	private Review rev;
	private Customer cust;
	
	// Method to implement sign up for a new customer
	@PostMapping(path="/signup",produces="application/json",consumes="application/json")
	public ResponseEntity<Customer> signUp(@RequestBody Customer customer){
		
		log.info("Customer Controller's signUp is called");
		
		 cust = customerServiceImpl.signUp(customer);
		 try {
		if(null!=cust) {
			return new ResponseEntity<Customer>(cust,HttpStatus.ACCEPTED);
		}
		 }
		 catch(Exception e) {
			 log.error(e.getMessage());
		 }
		throw new CustomerNotFoundException("Customer cannot be Added");
	}
	
	// Method to validate login credentials of a customer
	@GetMapping(path="/login",consumes="application/json")
	public ResponseEntity<Boolean> login(@RequestBody Customer customer){
		
		log.info("Customer Controller's login is called");
		
		boolean status = customerServiceImpl.validateLogin(customer);
		try {
		if(status==true) {
			return new ResponseEntity<Boolean>(status,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		throw new InvalidLoginCredentialsException("Invalid Credentials");
	}
	
	// Method to search a product
	@GetMapping(path="/product/search/{keyword}",produces="application/json")
	public ResponseEntity<List<Product>> getProductByDescription(@PathVariable("keyword") String keyword){
		
		log.info("Customer Controller's getProductByDescription is called");
		
		List<Product> category = (List<Product>) productServiceImpl.getProductDescription(keyword);
		try {
		if(category.size()>0) {
			return new ResponseEntity<List<Product>>(category,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		throw new ProductNotFoundException("Products not found");
	}

	// Method to display all products 
	@GetMapping(path="/product/view/",produces="application/json")
	public ResponseEntity<List<Product>> getAllProducts(){
		
		log.info("Customer Controller's getAllProducts is called");
		
		List<Product> list = (List<Product>) productServiceImpl.getAllProduct();
		try {
		if(list.size()>0) {
			return new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		throw new ProductNotFoundException("Products not found");
	}
	
	//Method to give review to the product
		@PostMapping(path="/product/review/{productName}/{billNumber}/{customerID}",produces="application/json",consumes="application/json")
		public ResponseEntity<Review> giveReview(@PathVariable("productName") String ProductName,@PathVariable("billNumber") String billNumber,@RequestBody Review review,@PathVariable("customerID") int customerID){
			
			log.info("Customer Controller's giveReview is called");
			
			if(billNumber==null) {
				return new ResponseEntity<Review>(review,HttpStatus.BAD_REQUEST);
			}
			
			rev = reviewServiceImpl.addReview(ProductName, review, customerID);
			try {
			if(null!=rev) {
				return new ResponseEntity<Review>(rev,HttpStatus.OK);
			}
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
			throw new ReviewNotFoundException("Review cannot be Added");
		}

	
	// Method to display top rated products by category
	@GetMapping(path="/product/view/best/{category}",produces="application/json")
	public ResponseEntity<List<Product>> getBestProduct(@PathVariable("category") String category){
		
		log.info("Customer Controller's getBestProduct is called");
		
		List<Product> product = (List<Product>) productServiceImpl.getBestProduct(category);
		try {
		if(product.size()>0) {
			return new ResponseEntity<List<Product>>(product,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());	
		}
		
		throw new ProductNotFoundException("Products not found");
	}
	
	// Method to display reviews of a product
	@GetMapping(path="/product/view/review/{productName}",produces="application/json")
	public ResponseEntity<List<Review>> getReview(@PathVariable("productName") String productName){
		
		log.info("Customer Controller's getReview is called");
		
		List<Review> list = (List<Review>) reviewServiceImpl.getReview(productName);
		try {
		if(list.size()>0) {
			return new ResponseEntity<List<Review>>(list,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());	
		}
		throw new ReviewNotFoundException("Product Review not found check product name");
	}
}
