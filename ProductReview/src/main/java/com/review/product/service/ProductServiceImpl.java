package com.review.product.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.product.model.Product;
import com.review.product.repository.ProductRepository;

//Service Class for Product
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	private Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepo;
	
	private Product prod;
	
	// Method to add a product to the database
	public Product addProduct(Product product) {
		if(product.getProductID()<=0) {
			return null;
		}
		log.debug("ProductService's addProduct method invoked");
		prod = productRepo.save(product);
		return prod;
	}
	
	// Method to delete a product from the database
	public boolean deleteProduct(int productID) {
		log.debug("ProductService's deleteProduct method invoked");
		
		 //prod = productRepo.findById(productID).get();
		 
		 if(productRepo.existsById(productID)) {
			 productRepo.deleteById(productID);
			 return true;
		 }
		 return false;
	}
	
	//Method to update a product's details in the database
	public Product updateProduct(int productID,Product product) {
		log.debug("ProductService's updateProduct method invoked");
		
		prod = productRepo.findById(productID).get();
		if(prod!=null) {
			prod.setProductName(product.getProductName());
			prod.setProductPrice(product.getProductPrice());
			prod.setProductDescription(product.getProductDescription());
			return prod;
		}
		return null;
	}
	
	//Method to display all products
	public List<Product> getAllProduct(){
		log.debug("ProductService's getAllProduct method invoked");
		List<Product> list =  (List<Product>) productRepo.findAll();
		return list;
	}
	
	//Method to display top rated products
	public List<Product> getBestProduct(String category){
		log.debug("ProductService's getCategory method invoked");
		List<Product> list = (List<Product>) productRepo.findBestProduct(category);
		return list;
	}
	
	// Method to find product
	public List<Product> getProductDescription(String keyword){
		log.debug("ProductService's getProductDescription method invoked");
		List<Product> product = (List<Product>) productRepo.findByProductDescription(keyword);
		return product;
	}
}
