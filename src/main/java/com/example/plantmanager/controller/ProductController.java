package com.example.plantmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantmanager.exception.ResourceNotFoundException;
import com.example.plantmanager.model.Operation;
import com.example.plantmanager.model.Product;
import com.example.plantmanager.model.RawMaterial;
import com.example.plantmanager.service.OperationService;
import com.example.plantmanager.service.ProductService;
import com.example.plantmanager.service.RawMaterialService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RawMaterialService rawMaterialService;
	
	@Autowired
	private OperationService operationService;
	
	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		return ResponseEntity.ok((productService.saveProduct(product)));
	}

	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProduct() {
		
		List<Product> product = new ArrayList<Product>();
		
		productService.getAllProduct().forEach(product::add);
		
		if(product.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(product,HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Optional<Product> product = productService.findById(id);
		
		if(product.isPresent()) {
			return new ResponseEntity<Product>(product.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> getMachineById(@PathVariable Long id,@RequestBody Product productdata) {
		Optional<Product> productfind = productService.findById(id);
		
		if(productfind.isPresent()) {
			
			Product product = productfind.get();
			product.setProductName(productdata.getProductName());
			product.setProductDesc(productdata.getProductDesc());
			return new ResponseEntity<Product>(productService.saveProduct(product),HttpStatus.OK);
		}
		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
		productService.deleteById(id);
		return new ResponseEntity<Product>(HttpStatus.OK);
	}
	
	@PostMapping("/product/{productid}/rawmaterial")
	public ResponseEntity<RawMaterial> addRawmaterial(@PathVariable Long productid,@RequestBody RawMaterial addrawMaterial) {
		
		RawMaterial rawMaterial = productService.findById(productid).map(product -> {
			long rawMaterialId = addrawMaterial.getId();
			
			if(rawMaterialId != 0L) {
				RawMaterial findRawMaterial = rawMaterialService.findById(rawMaterialId)
						.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND RAW MATERUAL WITH ID "+rawMaterialId));
				product.addRawMaterial(findRawMaterial);
				productService.saveProduct(product);
				return findRawMaterial;
			}
			
			product.addRawMaterial(addrawMaterial);
			return rawMaterialService.saveRawMaterial(addrawMaterial);
		}).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND PRODUCT WITH ID "+productid));
		
		return new ResponseEntity<RawMaterial>(rawMaterial,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{id}/rawmaterial")
	public ResponseEntity<List<RawMaterial>> getAllRawMaterialByProductId(@PathVariable Long id) {
		
		if(!productService.existsById(id)) {
			throw new ResourceNotFoundException("NOT FOUND PRODUCT WITH ID: " + id);
		}
		
		List<RawMaterial> rawmaterial = rawMaterialService.findRawMaterialsByProductsId(id);
		return new ResponseEntity<List<RawMaterial>>(rawmaterial,HttpStatus.OK);
	}
	
	@GetMapping("/rawmaterial/{id}/product")
	public ResponseEntity<List<Product>> getAllProductsByRawMaterialId(@PathVariable Long id) {
		
		if(!rawMaterialService.existsById(id)) {
			throw new ResourceNotFoundException("NOT FOUND PRODUCT WITH ID: " + id);
		}
		
		List<Product> product = productService.findProductsByRawMaterialsId(id);
		return new ResponseEntity<List<Product>>(product,HttpStatus.OK);
	}
	
	@DeleteMapping("/product/{productid}/rawmaterial/{rawmaterialid}")
	public ResponseEntity<?> deleteRawMaterialFromProduct(@PathVariable Long productid, @PathVariable Long rawmaterialid) {
		
		Product product = productService.findById(productid)
				.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND MACHINE WITH ID: " + productid));
		
		product.removeRawMaterial(rawmaterialid);
		productService.saveProduct(product);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/product/{productid}/operation")
	public ResponseEntity<Operation> addOperation(@PathVariable Long productid,@RequestBody Operation addoperation) {
		
		Operation operation = productService.findById(productid).map(product -> {
			long operationId = addoperation.getId();
			
			if(operationId != 0L) {
				Operation findOperation = operationService.findById(operationId)
						.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND RAW MATERUAL WITH ID "+operationId));
				product.addOperation(findOperation);
				productService.saveProduct(product);
				return findOperation;
			}
			
			product.addOperation(addoperation);
			return operationService.saveOperation(addoperation);
		}).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND PRODUCT WITH ID "+productid));
		
		return new ResponseEntity<Operation>(operation,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{id}/operation")
	public ResponseEntity<List<Operation>> getAllOperationByProductId(@PathVariable Long id) {
		
		if(!productService.existsById(id)) {
			throw new ResourceNotFoundException("NOT FOUND PRODUCT WITH ID: " + id);
		}
		
		List<Operation> operation = operationService.findOperationsByProductsId(id);
		return new ResponseEntity<List<Operation>>(operation,HttpStatus.OK);
	}
	
	@GetMapping("/operation/{id}/product")
	public ResponseEntity<List<Product>> getAllProductsByOperationId(@PathVariable Long id) {
		
		if(!operationService.existsById(id)) {
			throw new ResourceNotFoundException("NOT FOUND OPERATION WITH ID: " + id);
		}
		
		List<Product> product = productService.findProductsByOperationsId(id);
		return new ResponseEntity<List<Product>>(product,HttpStatus.OK);
	}
	
	@DeleteMapping("/product/{productid}/operation/{operationid}")
	public ResponseEntity<?> deleteOperationFromProduct(@PathVariable Long productid, @PathVariable Long operationid) {
		
		Product product = productService.findById(productid)
				.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND PRODUCT WITH ID: " + productid));
		
		product.removeOperation(operationid);
		productService.saveProduct(product);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
