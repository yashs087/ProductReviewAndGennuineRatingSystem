package com.review.product.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.product.model.Customer;
import com.review.product.model.Product;
import com.review.product.model.Review;
import com.review.product.repository.CustomerRepository;
import com.review.product.repository.ProductRepository;
import com.review.product.repository.ReviewRepository;


//Service class for Review
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{
	
	private Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	@Autowired
	private ReviewRepository reviewRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CustomerRepository customerRepo;
	
	private Product prod;
	private Review rev;
	
	// Method to display all reviews
	public List<Review> getAllReview(){
		
		log.debug("ReviewService's getAllReview method invoked");
		List<Review> list = (List<Review>) reviewRepo.findAll();
		return list;
	}
	
	// Method to add review 
		public Review addReview(String productName,Review review,int customerID) {
			log.debug("ReviewService's addReview method invoked");
			Product prod = productRepo.findByProductName(productName);
			Customer customer = customerRepo.findById(customerID).get();
			if(productRepo.existsById(prod.getProductID())) {
				rev = reviewRepo.save(review);
				rev.setCustomer(customer);
				rev.setProductID(prod);
				if(null==rev) {
					return null;
				}
			}
			return rev;
		}

	
	// Method to display reviews of a product
	public List<Review> getReview(String productName){
		log.debug("ReviewService's getReview method invoked");
		prod = productRepo.findByProductName(productName);
		List<Review> list = reviewRepo.findAllByProductID(prod.getProductID());
		return list;
	}

}
