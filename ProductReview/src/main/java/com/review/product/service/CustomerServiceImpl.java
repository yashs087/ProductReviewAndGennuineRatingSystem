package com.review.product.service;



import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.product.model.Customer;
import com.review.product.repository.CustomerRepository;
import com.review.product.validation.Validate;

//Service Class for Customer
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	private Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private Validate validate;
	
	private Customer cust;
	
	// Method to add details of a new customer
	public Customer signUp(Customer customer) {
		log.debug("CustomerService's signUp method invoked");
		if(validate.validate(customer)) {
			cust = customerRepo.save(customer);
			return cust;
		}
		return null;
	}
	
	/**
	 *  Method to validate login credentials of a customer
	 */
	public Boolean validateLogin(Customer customer) {
		log.debug("CustomerService's validateLogin method invoked");
		cust = customerRepo.findCustomerByUsernameAndPassword(customer.getCustUsername(),customer.getCustPassword());
		if(cust==null) {
			return false;
		}
		return true;
	}
	
	/**
	 *  Method to display list of all customers
	 */
	public List<Customer> getAllCustomer(){
		log.debug("CustomerService's getAllCustomer method invoked");
		List<Customer> list = (List<Customer>) customerRepo.findAll();
		return list;
	}
}
