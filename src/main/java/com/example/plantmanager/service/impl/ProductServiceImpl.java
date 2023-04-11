package com.example.plantmanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.Product;
import com.example.plantmanager.repository.ProductRepository;
import com.example.plantmanager.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findProductsByRawMaterialsId(Long id) {
		return productRepository.findProductsByRawMaterialsId(id);
	}

	@Override
	public Boolean existsById(Long id) {
		return productRepository.existsById(id);
	}

	@Override
	public List<Product> findProductsByOperationsId(Long id) {
		return productRepository.findProductsByOperationsId(id);
	}

}
