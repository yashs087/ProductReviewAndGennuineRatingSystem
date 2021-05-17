package com.review.product.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//POJO class for Customer
@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	private int customerID;
	private String custUsername;
	private String custPassword;
	private String custAddress;
	private long custPhone;
	
	public Customer() {
		
	}
	
	public Customer(int customerID, String custUsername, String custPassword, String custAddress, long custPhone) {
		super();
		this.customerID = customerID;
		this.custUsername = custUsername;
		this.custPassword = custPassword;
		this.custAddress = custAddress;
		this.custPhone = custPhone;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustUsername() {
		return custUsername;
	}
	public void setCustUsername(String custUsername) {
		this.custUsername = custUsername;
	}
	public String getCustPassword() {
		return custPassword;
	}
	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public long getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(long custPhone) {
		this.custPhone = custPhone;
	}
}
