package com.review.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.review.product.model.Review;

//Repository interface for Review
public interface ReviewRepository extends CrudRepository<Review,Integer>{

	@Query(value="select * from review r where r.productid= ?1",nativeQuery=true)
	List<Review> findAllByProductID(int productID);
	
	@Query(value="select avg(r.product_rating) from review r where r.productid = ?1",nativeQuery=true)
	Float findByProductID(int productID);

}
