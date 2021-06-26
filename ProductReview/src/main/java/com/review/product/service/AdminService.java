package com.review.product.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.product.model.Admin;

import com.review.product.repository.AdminRepository;

//Service class for Administrator
@Service
@Transactional
public class AdminService {
	
	private Logger log = LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private AdminRepository adminRepo;
	
	private Admin adm;
	
	// Method to validate Login credentials of Administrator
	public Boolean validateLogin(Admin admin) {
		log.debug("AdminService's validateLogin method invoked");
		adm = adminRepo.findAdminByUsernameAndPassword(admin.getAdminUsername(),admin.getAdminPassword());
		if(null==adm) {
			return false;
		}
		return true;
	}
	
	public Admin addAdmin(Admin admin) {
		adm = adminRepo.save(admin);
		return adm;
	}
}
