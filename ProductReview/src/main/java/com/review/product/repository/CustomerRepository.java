package com.review.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.review.product.model.Customer;

//Repository interface for Customer
public interface CustomerRepository extends CrudRepository<Customer,Integer>{

	@Query(value="select * from customer c where c.cust_username=?1 and c.cust_password=?2",nativeQuery=true)
	Customer findCustomerByUsernameAndPassword(String custUsername, String custPassword);
	
}
