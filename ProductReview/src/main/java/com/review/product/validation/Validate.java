package com.review.product.validation;

import java.util.regex.*;

import org.springframework.stereotype.Component;

import com.review.product.model.Customer;


//Validation Class
@Component
public class Validate {
	
	//Method to validate customer details
	public Boolean validate(Customer customer) {
		if(checkPassword(customer.getCustPassword()) && checkUsername(customer.getCustUsername()) && checkPhone(customer.getCustPhone())) {
			return true;
		}
		return false;
	}
	
	//Method to validate password
	private Boolean checkPassword(String password) {
		
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,15}$";
		Pattern pattern = Pattern.compile(regex);
		
		if(password==null) {
			return false;
		}
		
		Matcher match = pattern.matcher(password);
		return match.matches();
	}
	
	//Method to validate Username
	private Boolean checkUsername(String username) {
		if(username!=null && username.length()>5) {
			return true;
		}
		return false;
	}
	
	//Method to validate PhoneNumber
	private Boolean checkPhone(long phoneNumber) {
		String phone = String.valueOf(phoneNumber);
		if(phone.length() ==  10) {
			return true;
		}
		return false;
	}
}
