package com.example.plantmanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.Operation;
import com.example.plantmanager.repository.OperationRepository;
import com.example.plantmanager.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService{

	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public Operation saveOperation(Operation operation) {
		return operationRepository.save(operation);
	}

	@Override
	public List<Operation> getAllOperation() {
		return operationRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		operationRepository.deleteById(id);
	}

	@Override
	public Optional<Operation> findById(Long id) {
		return operationRepository.findById(id);
	}

	@Override
	public List<Operation> findOperationsByMachinesId(Long id) {
		return operationRepository.findOperationsByMachinesId(id);
	}

	@Override
	public Boolean existsById(Long id) {
		return operationRepository.existsById(id);
	}

	@Override
	public List<Operation> findOperationsByProductsId(Long id) {
		return operationRepository.findOperationsByProductsId(id);
	}

}
