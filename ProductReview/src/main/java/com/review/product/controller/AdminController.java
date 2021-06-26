package com.review.product.controller;

import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.review.product.exception.CustomerNotFoundException;
import com.review.product.exception.InvalidLoginCredentialsException;
import com.review.product.exception.ProductNotFoundException;
import com.review.product.exception.ReviewNotFoundException;
import com.review.product.model.Admin;
import com.review.product.model.Customer;
import com.review.product.model.Product;
import com.review.product.model.Review;
import com.review.product.service.AdminService;
import com.review.product.service.CustomerService;
import com.review.product.service.ProductService;
import com.review.product.service.ReviewService;

//Controller class for Administrator
@CrossOrigin
@RestController
@RequestMapping("/Admin")
public class AdminController {
	
	private Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerService custService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	
	private Product prod;
	

		@PostMapping(path="/login",produces="application/json",consumes="application/json")
		public ResponseEntity<String> validateLogin(@RequestBody Admin admin){
		
		log.info("Admin Controller's validateLogin is called");
		
		Boolean status = adminService.validateLogin(admin);
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
	
	@PostMapping(path="/admin/add",consumes="application/json",produces="application/json")
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
		Admin adm = adminService.addAdmin(admin);
		return new ResponseEntity<Admin>(admin,HttpStatus.OK);
	}
		
	// Method to add product details to the database
	@PostMapping(path="/product/add/",consumes="application/json",produces="application/json")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		
		log.info("Admin Controller's addProduct is called");
		
		prod = productService.addProduct(product);
		try {
		if(null!=prod) {
			return new ResponseEntity<Product>(prod,HttpStatus.ACCEPTED);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		throw new ProductNotFoundException("Product Cannot be Added");
	}
	
	// Method to delete product from database
	@DeleteMapping(path="/product/delete/{productID}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable("productID") int productID){
		
		log.info("Admin Controller's deleteProduct is called");
		
		boolean status = productService.deleteProduct(productID);
		
		try {
		if(status==true) {
			return new ResponseEntity<Boolean>(status,HttpStatus.OK);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		throw new ProductNotFoundException("Product cannot be deleted");
	}
	
	// Method to display all products present in the database
	@GetMapping(path="product/view/",produces="application/json")
	public ResponseEntity<List<Product>> getAllProduct(){
		
		log.info("Admin Controller's getAllProduct is called");
		
		List<Product> list = productService.getAllProduct();
		try {
		if(list.size()>0) {
			return new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());	
		}
		throw new ProductNotFoundException("No Products Found");
	}
	
	//Method to update product in the database
	@PutMapping(path="/product/update/{productID}",produces="application/json",consumes="application/json")
	public ResponseEntity<Product> updateProduct(@PathVariable("productID") int productID,@RequestBody Product product){
		
		log.info("Admin Controller's updateProduct is called");
		
		prod = productService.updateProduct(productID,product);
		try {
		if(prod!=null) {
			return new ResponseEntity<Product>(prod,HttpStatus.OK);
			}
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		throw new ProductNotFoundException("Product with product id "+productID+" not found");
	}
	
	// Method to display all customers present in the database
	@GetMapping(path="/customer/view/",produces="application/json")
	public ResponseEntity<List<Customer>> getAllCustomer(){
		
		log.info("Admin Controller's getAllCustomer is called");
		
		List<Customer> list = custService.getAllCustomer();
		try {
		if(list.size()>0) {
			return new ResponseEntity<List<Customer>>(list,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());	
		}
		throw new CustomerNotFoundException("No Customer Found");
	}
	
	// Method to display all reviews present in the database
	@GetMapping(path="/review/view/",produces="application/json")
	public ResponseEntity<List<Review>> getAllReview(){
		
		log.info("Admin Controller's getAllReview is called");
		
		List<Review> list = reviewService.getAllReview();
		try {
		if(list.size()>0) {
			return new ResponseEntity<List<Review>>(list,HttpStatus.FOUND);
		}
		}
		catch(Exception e) {
			log.error(e.getMessage());	
		}
		throw new ReviewNotFoundException("No reviews Found");
	}
	
	@GetMapping(path="/product/view/{productID}",produces="application/json")
	public ResponseEntity<Product> getProduct(@PathVariable("productID") int productID){
		prod = productService.getProduct(productID);
		if(prod==null) {
			return new ResponseEntity<Product>(prod,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(prod,HttpStatus.FOUND);
	}
}
