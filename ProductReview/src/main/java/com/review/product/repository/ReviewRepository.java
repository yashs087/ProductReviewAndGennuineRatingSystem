package com.review.product.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.review.product.model.Review;

//Repository interface for Review
@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer>{

	
	List<Review> findAllByProductID(int productID);

}
