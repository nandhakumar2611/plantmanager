package com.example.plantmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantmanager.model.Operation;
import com.example.plantmanager.service.OperationService;

@RestController
@RequestMapping("api/v1/operation/")
public class OperationController {

	@Autowired
	private OperationService operationService;
	
	@PostMapping("/operation")
	public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) {
		
		return ResponseEntity.ok(operationService.saveOperation(operation));
	}
	
	@GetMapping("/operation")
	public ResponseEntity<List<Operation>> getAllOperations() {
		
		List<Operation> operation = new ArrayList<Operation>();
		
		operationService.getAllOperation().forEach(operation::add);
		
		if(operation.isEmpty()) {
			return new ResponseEntity<List<Operation>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Operation>>(operation,HttpStatus.OK);			
	}
	
	@GetMapping("/operation/{id}")
	public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
		Optional<Operation> operationData = operationService.findById(id);
		
		if(operationData.isPresent()) {
			return new ResponseEntity<Operation>(operationData.get(),HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/operation/{id}")
	public ResponseEntity<Operation> getOperationById(@PathVariable Long id,@RequestBody Operation operationdetails) {
		
		Optional<Operation> operationData = operationService.findById(id);
		
		if(operationData.isPresent()) {
			Operation operation = operationData.get();
			operation.setOperationName(operationdetails.getOperationName());
			operation.setOperationDesc(operationdetails.getOperationDesc());
			return new ResponseEntity<Operation>(operationService.saveOperation(operation),HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/operation/{id}")
	public ResponseEntity<?> deleteOperation(@PathVariable Long id) {
	
		operationService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
