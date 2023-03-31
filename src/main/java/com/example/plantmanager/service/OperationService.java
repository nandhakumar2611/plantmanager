package com.example.plantmanager.service;

import java.util.List;
import java.util.Optional;

import com.example.plantmanager.model.Operation;

public interface OperationService {
	
	Operation saveOperation(Operation operation);

	List<Operation> getAllOperation();
	
	Optional<Operation> findById(Long id);
	
	void deleteById(Long id);
	
	List<Operation> findOperationsByMachinesId(Long id);
	
	Boolean existsById(Long id);
}
