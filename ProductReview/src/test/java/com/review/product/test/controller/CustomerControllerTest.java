package com.review.product.test.controller;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.review.product.controller.CustomerController;
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

@SpringBootTest
public class CustomerControllerTest {

	@Autowired
	private CustomerController customerController;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private ReviewService reviewService;
	
	@MockBean
	private CustomerService customerService;
	
	@Test
	public void signUpTest() {
		Customer customer = new Customer(1,"ABCDEFG","Abcd@1234","Delhi",9090909090L);
		when(customerService.signUp(customer)).thenReturn(customer);
		ResponseEntity<Customer> expected = new ResponseEntity<Customer>(customer,HttpStatus.ACCEPTED);
		ResponseEntity<Customer> actual = customerController.signUp(customer);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void signUpWithInvalidUsernameTest() {
		Customer customer = new Customer(1,"ABC","Abcd@1234","Delhi",9090909090L);
		when(customerService.signUp(customer)).thenReturn(null);
		Exception e = Assertions.assertThrows(CustomerNotFoundException.class,() -> {customerController.signUp(customer);});
		Assertions.assertEquals("Customer cannot be Added", e.getMessage());
	}
	
	@Test
	public void signUpWithInvalidPasswordTest() {
		Customer customer = new Customer(1,"ABCDEF","abcd@12","Delhi",9090909090L);
		when(customerService.signUp(customer)).thenReturn(null);
		Exception e = Assertions.assertThrows(CustomerNotFoundException.class,() -> {customerController.signUp(customer);});
		Assertions.assertEquals("Customer cannot be Added", e.getMessage());
	}
	
	@Test
	public void signUpWithInvalidPhoneNumberTest() {
		Customer customer = new Customer(1,"ABC","Abcd@1234","Delhi",909);
		when(customerService.signUp(customer)).thenReturn(null);
		Exception e = Assertions.assertThrows(CustomerNotFoundException.class,() -> {customerController.signUp(customer);});
		Assertions.assertEquals("Customer cannot be Added", e.getMessage());
	}
	
	@Test
	public void loginTest() {
		Customer customer = new Customer(1,"ABCDEFG","Abcd@1234","Delhi",9090909090L);
		when(customerService.validateLogin(customer)).thenReturn(true);
		ResponseEntity<Boolean> expected = new ResponseEntity<Boolean>(true,HttpStatus.FOUND);
		ResponseEntity<String> actual = customerController.login(customer);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void loginWithNullCustomerTest() {
		Customer customer = null;
		when(customerService.validateLogin(customer)).thenReturn(false);
		Exception e = Assertions.assertThrows(InvalidLoginCredentialsException.class,() -> {customerController.login(customer);});
		Assertions.assertEquals("Invalid Credentials", e.getMessage());
	}
	
	@Test
	public void getProductByDescriptionTest() {
		List<Product> list = Stream.of(new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality"),new Product(2,"OnePlus 4","Mobile Phone",55000.00,"Great phone")).collect(Collectors.toList()); 
		when(productService.getProductDescription("Great")).thenReturn(list);
		ResponseEntity<List<Product>> expected = new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
		ResponseEntity<List<Product>> actual = customerController.getProductByDescription("Great");
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getProductByDescriptionNullListTest() {
		List<Product> list = null; 
		when(productService.getProductDescription("Great")).thenReturn(list);
		Exception e = Assertions.assertThrows(ProductNotFoundException.class,() -> {customerController.getProductByDescription("Great");});
		Assertions.assertEquals("Products not found", e.getMessage());
	}
	
	@Test
	public void getAllProductsTest() {
		List<Product> list = Stream.of(new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality"),new Product(2,"OnePlus 4","Mobile Phone",55000.00,"Great phone")).collect(Collectors.toList()); 
		when(productService.getAllProduct()).thenReturn(list);
		ResponseEntity<List<Product>> expected = new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
		ResponseEntity<List<Product>> actual = customerController.getAllProducts();
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getAllProductsWithNullListTest() {
		List<Product> list = null; 
		when(productService.getAllProduct()).thenReturn(list);
		Exception e = Assertions.assertThrows(ProductNotFoundException.class,() -> {customerController.getAllProducts();});
		Assertions.assertEquals("Products not found", e.getMessage());
	}
	
	@Test
	public void giveReviewTest() {
		String billNumber = "ABCDEF12345";
		Product p = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		Customer customer = new Customer(1,"ABCDEFG","Abcd@1234","Delhi",9090909090L);
		Review review = new Review(1,p,customer,4,"Good phone");
		when(reviewService.addReview(p.getProductName(), review,1)).thenReturn(review);
		ResponseEntity<Review> expected = new ResponseEntity<Review>(review,HttpStatus.OK);
		ResponseEntity<Review> actual = customerController.giveReview(p.getProductName(), billNumber, review,1);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void giveNullReviewTest() {
		String billNumber = "ABCDEF12345";
		Product p = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		Review review = null;
		when(reviewService.addReview(p.getProductName(), review,1)).thenReturn(review);
		Exception e = Assertions.assertThrows(ReviewNotFoundException.class,() -> {customerController.giveReview(p.getProductName(), billNumber, review,1);});
		Assertions.assertEquals("Review cannot be Added", e.getMessage());
	}
	
	@Test
	public void getBestProductTest() {
		List<Product> list = Stream.of(new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality"),new Product(2,"OnePlus 4","Mobile Phone",55000.00,"Great phone")).collect(Collectors.toList());
		when(productService.getBestProduct("Mobile Phone")).thenReturn(list);
		ResponseEntity<List<Product>> expected = new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
		ResponseEntity<List<Product>> actual = customerController.getBestProduct("Mobile Phone");
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getBestProductWithNullListTest() {
		List<Product> list = null;
		when(productService.getBestProduct("Mobile Phone")).thenReturn(list);
		Exception e = Assertions.assertThrows(ProductNotFoundException.class,() -> {customerController.getBestProduct("Mobile Phone");});
		Assertions.assertEquals("Products not found", e.getMessage());
	}
	
	@Test
	public void getReviewTest() {
		Customer customer = new Customer(1,"ABCDEFG","Abcd@1234","Delhi",9090909090L);
		Customer customer2 = new Customer(2,"Abcdefg","Abcd@1234","Delhi",9090909091L);
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		List<Review> reviewList = Stream.of(new Review(1,product,customer,4,"Good product"),new Review(2,product,customer2,5,"Great Product")).collect(Collectors.toList());
		Product p = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(reviewService.getReview(p.getProductName())).thenReturn(reviewList);
		ResponseEntity<List<Review>> expected = new ResponseEntity<List<Review>>(reviewList,HttpStatus.FOUND);
		ResponseEntity<List<Review>> actual = customerController.getReview(p.getProductName());
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getReviewWithNullListTest() {
		List<Review> reviewList = null;
		Product p = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(reviewService.getReview(p.getProductName())).thenReturn(reviewList);
		Exception e = Assertions.assertThrows(ReviewNotFoundException.class,() -> {customerController.getReview(p.getProductName());});
		Assertions.assertEquals("Product Review not found check product name", e.getMessage());
	}
}
