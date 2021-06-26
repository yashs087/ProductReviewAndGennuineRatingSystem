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
import com.review.product.controller.AdminController;
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


@SpringBootTest
public class AdminControllerTest {
	
	
	@Autowired
	private AdminController adminController;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private ReviewService reviewService;
	
	@MockBean
	private AdminService adminService;
	
	@Test
	public void validateLoginTest() {
		Admin admin = new Admin(1,"Admin","1234");
		when(adminService.validateLogin(admin)).thenReturn(true);
		ResponseEntity<Boolean> expected = new ResponseEntity<Boolean>(true,HttpStatus.OK);
		ResponseEntity<String> actual = adminController.validateLogin(admin);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test()
	public void validateLoginInvalidUsernameTest() {
		Admin admin = new Admin(1,"Adm","1234");
		when(adminService.validateLogin(admin)).thenReturn(false);
		Exception e = Assertions.assertThrows(InvalidLoginCredentialsException.class,() -> {adminController.validateLogin(admin);});
		Assertions.assertEquals("Invalid Credentials", e.getMessage());
	}
	
	@Test
	public void validateLoginInvalidPasswordTest() {
		Admin admin = new Admin(1,"Admin","123");
		when(adminService.validateLogin(admin)).thenReturn(false);
		Exception e = Assertions.assertThrows(InvalidLoginCredentialsException.class,() -> {adminController.validateLogin(admin);});
		Assertions.assertEquals("Invalid Credentials", e.getMessage());
	}
	
	@Test
	public void addProductTest() {
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(productService.addProduct(product)).thenReturn(product);
		ResponseEntity<Product> expected = new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
		ResponseEntity<Product> actual = adminController.addProduct(product);
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void addNullProductTest() {
		Product product = null;
		when(productService.addProduct(product)).thenReturn(product);
		Exception e = Assertions.assertThrows(ProductNotFoundException.class,() -> {adminController.addProduct(product);});
		Assertions.assertEquals("Product Cannot be Added", e.getMessage());
	}
	
	@Test
	public void deleteProductTest() {
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(productService.deleteProduct(product.getProductID())).thenReturn(true);
		ResponseEntity<Boolean> expected = new ResponseEntity<Boolean>(true,HttpStatus.OK);
		ResponseEntity<Boolean> actual = adminController.deleteProduct(product.getProductID());
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void deleteNullProductTest() {
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(productService.deleteProduct(2)).thenReturn(false);
		Exception e = Assertions.assertThrows(ProductNotFoundException.class,() -> {adminController.deleteProduct(product.getProductID());});
		Assertions.assertEquals("Product cannot be deleted", e.getMessage());
	}
	
	@Test
	public void getAllProductsTest() {
		List<Product> list = Stream.of(new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality"),new Product(2,"OnePlus 4","Mobile Phone",55000.00,"Great phone")).collect(Collectors.toList());
		when(productService.getAllProduct()).thenReturn(list);
		ResponseEntity<List<Product>> expected = new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
		ResponseEntity<List<Product>> actual = adminController.getAllProduct();
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getAllProductsForNullListTest() {
		List<Product> list = null;
		when(productService.getAllProduct()).thenReturn(list);
		Exception e = Assertions.assertThrows(ProductNotFoundException.class,() -> {adminController.getAllProduct();});
		Assertions.assertEquals("No Products Found", e.getMessage());
	}
	
	@Test
	public void updateProductTest() {
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		product.setProductName("Iphone 9");
		when(productService.updateProduct(product.getProductID(),product)).thenReturn(product);
		ResponseEntity<Product> expected = new ResponseEntity<Product>(product,HttpStatus.OK);
		ResponseEntity<Product> actual = adminController.updateProduct(product.getProductID(), product);
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getAllCustomersTest() {
		List<Customer> customerList = Stream.of(new Customer(1,"ABCDEFG","Abcd@1234","Delhi",9090909090L), new Customer(2,"ABCXYZW","Wxyz@1234","Delhi",9090909091L)).collect(Collectors.toList());
		when(customerService.getAllCustomer()).thenReturn(customerList);
		ResponseEntity<List<Customer>> expected = new ResponseEntity<List<Customer>>(customerList,HttpStatus.FOUND);
		ResponseEntity<List<Customer>> actual = adminController.getAllCustomer();
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getAllNullCustomersTest() {
		List<Customer> customerList = null;
		when(customerService.getAllCustomer()).thenReturn(customerList);
		Exception e = Assertions.assertThrows(CustomerNotFoundException.class,() -> {adminController.getAllCustomer();});
		Assertions.assertEquals("No Customer Found", e.getMessage());
	}
	
	@Test
	public void getAllReviewTest() {
		Customer customer = new Customer(1,"ABCDEFG","Abcd@1234","Delhi",9090909090L);
		Customer customer2 = new Customer(2,"Abcdefg","Abcd@1234","Delhi",9090909091L);
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		List<Review> reviewList = Stream.of(new Review(2,product,customer,4,"Good product"),new Review(1,product,customer2,5,"Great Product")).collect(Collectors.toList());
		when(reviewService.getAllReview()).thenReturn(reviewList);
		ResponseEntity<List<Review>> expected = new ResponseEntity<List<Review>>(reviewList,HttpStatus.FOUND);
		ResponseEntity<List<Review>> actual = adminController.getAllReview();
		Assertions.assertEquals(expected,actual);
	}
	
	@Test
	public void getAllNullReviewTest() {
		List<Review> reviewList = null;
		when(reviewService.getAllReview()).thenReturn(reviewList);
		Exception e = Assertions.assertThrows(ReviewNotFoundException.class,() -> {adminController.getAllReview();});
		Assertions.assertEquals("No reviews Found", e.getMessage());
	}
}
