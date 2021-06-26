package com.review.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.review.product.model.Admin;

//Repository interface for Administrator
public interface AdminRepository extends CrudRepository<Admin,String>{
	
	@Query(value="SELECT * FROM ADMIN a where a.admin_username = ?1 and a.admin_password = ?2",nativeQuery=true)
	 public Admin findAdminByUsernameAndPassword(String adminUsername,String adminPassword);
}
