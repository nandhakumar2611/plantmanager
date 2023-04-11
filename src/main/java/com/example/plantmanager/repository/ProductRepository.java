package com.example.plantmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plantmanager.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findProductsByRawMaterialsId(Long id);
	
	List<Product> findProductsByOperationsId(Long id);
}
