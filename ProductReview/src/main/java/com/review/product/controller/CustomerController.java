package com.review.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.review.product.service.CustomerService;
import com.review.product.service.ProductService;
import com.review.product.service.ReviewService;

//Controller class for Customer
@CrossOrigin
@RestController
@RequestMapping("/Customer")
public class CustomerController {
	
	private Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	
	private Review rev;
	private Customer cust;
	
	// Method to implement sign up for a new customer
		@PostMapping(path="/signup",produces="application/json",consumes="application/json")
		public ResponseEntity<Customer> signUp(@RequestBody Customer customer){
			
			log.info("Customer Controller's signUp is called");
			
			 cust = customerService.signUp(customer);
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
		@PostMapping(path="/login",consumes="application/json",produces="application/json")
		public ResponseEntity<String> login(@RequestBody Customer customer){
			
			log.info("Customer Controller's login is called");
			
			Boolean status = customerService.validateLogin(customer);
			String result = Boolean.toString(status);
			try {
			if(status==true) {
				return new ResponseEntity<String>(result,HttpStatus.OK);
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
		
		List<Product> category = (List<Product>) productService.getProductDescription(keyword);
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
		
		List<Product> list = (List<Product>) productService.getAllProduct();
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
	@PostMapping(path="product/review/{productName}/{billNumber}/{customerID}",produces="application/json",consumes="application/json")
	public ResponseEntity<Review> giveReview(@PathVariable("productName") String ProductName,@PathVariable("billNumber") String billNumber,@RequestBody Review review,@PathVariable("customerID") int customerID){
		
		log.info("Customer Controller's giveReview is called");
		
		if(billNumber==null) {
			return new ResponseEntity<Review>(review,HttpStatus.BAD_REQUEST);
		}
		
		rev = reviewService.addReview(ProductName, review,customerID);
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
	@GetMapping(path="product/view/best/{category}",produces="application/json")
	public ResponseEntity<List<Product>> getBestProduct(@PathVariable("category") String category){
		
		log.info("Customer Controller's getBestProduct is called");
		
		List<Product> product = (List<Product>) productService.getBestProduct(category);
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
	@GetMapping(path="product/view/review/{productName}",produces="application/json")
	public ResponseEntity<List<Review>> getReview(@PathVariable("productName") String productName){
		
		log.info("Customer Controller's getReview is called");
		
		List<Review> list = (List<Review>) reviewService.getReview(productName);
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
	
	@GetMapping(path="product/view/review/rating/{productName}",produces="application/json")
	public ResponseEntity<Float> getAverageRating(@PathVariable("productName") String productName){
		
		Float val = reviewService.getAverageRating(productName);
		
		try {
			if(val==0) {
				return new ResponseEntity<Float>(val,HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception e) {
			log.error(e.getMessage());	
		}
		return new ResponseEntity<Float>(val,HttpStatus.FOUND);
	}
}
