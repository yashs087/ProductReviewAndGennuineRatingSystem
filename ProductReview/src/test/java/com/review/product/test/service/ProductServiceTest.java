package com.review.product.test.service;

import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.review.product.model.Product;
import com.review.product.repository.ProductRepository;
import com.review.product.service.ProductService;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Test
	public void addProductTest() {
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(productRepository.save(product)).thenReturn(product);
		Assertions.assertEquals(product, productService.addProduct(product));
	}
	
	@Test
	public void addProductWithNegativeIdTest() {
		Product product = new Product(-1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(productRepository.save(product)).thenReturn(product);
		Assertions.assertEquals(null, productService.addProduct(product));
	}
	
	@Test
	public void addProductWithIdAsZeroTest() {
		Product product = new Product(0,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		when(productRepository.save(product)).thenReturn(product);
		Assertions.assertEquals(null, productService.addProduct(product));
	}
	
	@Test
	public void deleteProductTest() {
		Product product = new Product(2,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		productService.addProduct(product);
		boolean result = productService.deleteProduct(product.getProductID());
		Assertions.assertEquals(true,result);
		
		//verify(productRepository,times(1)).deleteById(product.getProductID());
	}
	
	@Test
	public void updateProductTest() {
		Product product = new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality");
		productService.addProduct(product);
		product.setProductName("Iphone 11");
		when(productRepository.save(product)).thenReturn(product);
		Assertions.assertEquals(product, productService.updateProduct(product.getProductID(),product));
	}
	
	@Test
	public void getAllProductsTest() {
		when(productRepository.findAll()).thenReturn(Stream.of(new Product(1,"Iphone 10","Mobile Phone",60000.00,"Great quality"),new Product(2,"OnePlus 4","Mobile Phone",55000.00,"Good phone")).collect(Collectors.toList()));
		Assertions.assertEquals(2, productService.getAllProduct().size());
	}
}
