package com.review.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.review.product.model.Product;

//Repository interface for Product
@Repository
public interface ProductRepository extends CrudRepository<Product,Integer>{

	Product findByProductName(String productName);	
	
	@Query(value="select * from Product a where a.productID in (Select b.productid from Review b group by b.productid order by avg(b.product_rating) desc) and a.product_category = ?1",nativeQuery=true)
	public List<Product> findBestProduct(String category);

	@Query(value="select product_category from product p",nativeQuery=true)
	public List<Product> getProductCategory();

	@Query(value="select * from product a where a.product_description like ?1%",nativeQuery=true)
	List<Product> findByProductDescription(String keyword);
	
}
