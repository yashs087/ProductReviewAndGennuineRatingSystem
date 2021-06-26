package com.review.product.test.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.review.product.model.Customer;
import com.review.product.repository.CustomerRepository;
import com.review.product.service.CustomerService;

@SpringBootTest
public class CustomerServiceTest{
	
	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@Test
	public void signUpTest() {
		Customer customer = new Customer(1,"ABCDEF","Abcd@1234","XYZ",9090909090L);
		when(customerRepository.save(customer)).thenReturn(customer);
		Assertions.assertEquals(customer, customerService.signUp(customer));
	}
	
	@Test
	public void signUpWithInvalidPasswordTest() {
		Customer customer = new Customer(1,"abcde","abcd","XYZ",9090909090L);
		when(customerRepository.save(customer)).thenReturn(customer);
		Assertions.assertEquals(null, customerService.signUp(customer));
	}
	
	@Test
	public void signUpWithInvalidUsername() {
		Customer customer = new Customer(1,"abcde","Abcd@1234","XYZ",9090909090L);
		when(customerRepository.save(customer)).thenReturn(customer);
		Assertions.assertEquals(null, customerService.signUp(customer));
	}
	
	@Test
	public void signUpWithInvalidPhoneNumber() {
		Customer customer = new Customer(1,"abcde","Abcd@1234","XYZ",9090);
		when(customerRepository.save(customer)).thenReturn(customer);
		Assertions.assertEquals(null, customerService.signUp(customer));
	}
}
