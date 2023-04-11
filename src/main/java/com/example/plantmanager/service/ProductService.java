package com.example.plantmanager.service;

import java.util.List;
import java.util.Optional;

import com.example.plantmanager.model.Product;

public interface ProductService {

	Product saveProduct(Product product);
	
	List<Product> getAllProduct();
	
	Optional<Product> findById(Long id);
	
	void deleteById(Long id);
	
	List<Product> findProductsByRawMaterialsId(Long id);
	
	List<Product> findProductsByOperationsId(Long id);
	
	Boolean existsById(Long id);
}
