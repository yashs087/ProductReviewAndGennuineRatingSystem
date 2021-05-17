package com.review.product.test.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.review.product.model.Admin;
import com.review.product.repository.AdminRepository;
import com.review.product.service.AdminServiceImpl;

@SpringBootTest
public class AdminServiceTest{
	
	//@Autowired
	//private WebApplicationContext webApplicationContext;
	
	@Autowired
	private AdminServiceImpl adminServiceImpl;
	
	@MockBean
	private AdminRepository adminRepository;
	
	
	
	@Test
	public void validateLoginTest() {
		Admin admin = new Admin(1,"admin","1234");
		when(adminRepository.findAdminByUsernameAndPassword(admin.getAdminUsername(),admin.getAdminPassword())).thenReturn(admin);
		Assertions.assertEquals(true, adminServiceImpl.validateLogin(admin));
	}
	
	@Test
	public void validateLoginForIncorrectPasswordTest() {
		Admin admin = new Admin(1,"admin","123");
		when(adminRepository.findAdminByUsernameAndPassword(admin.getAdminUsername(),admin.getAdminPassword())).thenReturn(null);
		Assertions.assertEquals(false, adminServiceImpl.validateLogin(admin));
	}
	
	@Test
	public void validateLoginForIncorrectUsernameTest() {
		Admin admin = new Admin(1,"adm","1234");
		when(adminRepository.findAdminByUsernameAndPassword(admin.getAdminUsername(),admin.getAdminPassword())).thenReturn(null);
		Assertions.assertEquals(false, adminServiceImpl.validateLogin(admin));
	}
}
