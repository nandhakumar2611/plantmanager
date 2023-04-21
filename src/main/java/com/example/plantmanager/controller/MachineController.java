package com.example.plantmanager.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.example.plantmanager.dto.MachineRequest;
import com.example.plantmanager.exception.ResourceNotFoundException;
import com.example.plantmanager.model.Machine;
import com.example.plantmanager.model.Operation;
import com.example.plantmanager.service.MachineService;
import com.example.plantmanager.service.OperationService;

/**
 * @author Nandha Kumar
 * @version 1.0
 * @since March 2023
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/machine")
public class MachineController {
	
	@Autowired
	private MachineService machineService;
	
	@Autowired
	private OperationService operationService;
	
	@PostMapping("/machine")
	public ResponseEntity<Machine> createMachine(@RequestBody Machine machine) {
		return ResponseEntity.ok(machineService.saveMachine(machine));
	}
	
	@PostMapping("/createmachine")
	public ResponseEntity<?> saveMachine(@RequestBody MachineRequest machineRequest) {
		
		Machine machine = new Machine(machineRequest.getMachineName(), machineRequest.getMachineDesc());
		Set<Long> machineOperations = machineRequest.getOperation();
		Set<Operation> operations = new HashSet<>();
		if(machineOperations == null) {
			System.out.println("OPERATION ARE NULL");
		}
		else {
			machineOperations.forEach(operation -> {
				Operation findoperation = operationService.findById(operation).
						orElseThrow(() -> new RuntimeException("OPERATION NOT FOUND"));
				operations.add(findoperation);
			});
			machine.setOperations(operations);
			machineService.saveMachine(machine);
		}
		return ResponseEntity.ok(new MessageResponse("MACHINE SAVED SUCCESSFULLY!..."));
	}
	
	@GetMapping("/machine")
	public ResponseEntity<List<Machine>> getAllMachine() {
		
		List<Machine> machine = new ArrayList<Machine>();
		
		machineService.getAllMachine().forEach(machine::add);
		
		if(machine.isEmpty()) {
			return new ResponseEntity<List<Machine>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Machine>>(machine,HttpStatus.OK);
	}
	
	@GetMapping("/machine/{id}")
	public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
		
		Optional<Machine> machineData = machineService.findById(id);
		
		if(machineData.isPresent()) {
			return new ResponseEntity<Machine>(machineData.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Machine>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/machines/{machinename}")
	public ResponseEntity<Machine> getMachineByName(@PathVariable String machinename) {
		Machine machineData = machineService.findByMachineName(machinename);
		
		if(machineData != null) {
			return new ResponseEntity<Machine>(machineData,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Machine>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/machine/{id}")
	public ResponseEntity<Machine> getMachineById(@PathVariable Long id,@RequestBody Machine machinedetails) {
		Optional<Machine> machineData = machineService.findById(id);
		
		if(machineData.isPresent()) {
			Machine machine = machineData.get();
			machine.setMachineName(machinedetails.getMachineName());
			machine.setMachineDesc(machinedetails.getMachineDesc());
			return new ResponseEntity<Machine>(machineService.saveMachine(machine),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Machine>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/machine/{id}")
	public ResponseEntity<Machine> deleteMachine(@PathVariable Long id) {

		machineService.deleteById(id);
		return new ResponseEntity<Machine>(HttpStatus.OK);
	}
	
	@GetMapping("/machines/{id}/operations")
	public ResponseEntity<List<Operation>> getAllOperationByMachineId(@PathVariable Long id) {
		
		if(!machineService.existsById(id)) {
			throw new ResourceNotFoundException("NOT FOUND MACHINE WITH ID: " + id);
		}
		
		List<Operation> operation = operationService.findOperationsByMachinesId(id);
		return new ResponseEntity<List<Operation>>(operation,HttpStatus.OK);
	}
	
	@GetMapping("/operations/{id}/machines")
	public ResponseEntity<List<Machine>> getAllMachineByOperationId(@PathVariable Long id) {
		
		if(!operationService.existsById(id)) {
			throw new ResourceNotFoundException("NOT FOUND OPERATION WITH ID: " + id);
		}
		
		List<Machine> machine = machineService.findMachinesByOperationsId(id);
		return new ResponseEntity<List<Machine>>(machine,HttpStatus.OK);
	}
	
	
	@PutMapping("/{machineid}/operation")
	public Machine assignOperationsToMachine(@PathVariable Long machineid,@RequestBody List<Long> operationid) {
		
		Set<Operation> operationset = null;
		Machine machine = machineService.findById(machineid).get();
		for(int i=0;i<operationid.size();i++) {
			Operation operation = operationService.findById(operationid.get(i)).get();
			operationset = machine.getOperations();
			operationset.add(operation);
		}
		machine.setOperations(operationset);
		return machineService.saveMachine(machine);
	}

	@PostMapping("/{machineId}/operations")
	public ResponseEntity<Machine> addOperationToMachine(@PathVariable Long machineId, @RequestBody List<Long> operationIds) {
		
		Optional<Machine> machine = machineService.findById(machineId);
		if(machine.isPresent()) {
			Set<Operation> operations = new HashSet<>();
			for(Long operationId : operationIds) {
				Optional<Operation> operation = operationService.findById(operationId);
				if(operation.isPresent()) {
					operations.add(operation.get());
				}
			}
			machine.get().getOperations().addAll(operations);
			machineService.saveMachine(machine.get());
			return ResponseEntity.ok(machine.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{machineId}/operations/{operationId}")
	public ResponseEntity<Machine> removeOperationFromMachine(@PathVariable Long machineId, @PathVariable Long operationId) {
		
		Optional<Machine> machine = machineService.findById(machineId);
		Optional<Operation> operation = operationService.findById(operationId);
		if(machine.isPresent() && operation.isPresent()) {
			machine.get().getOperations().remove(operation.get());
			machineService.saveMachine(machine.get());
			return ResponseEntity.ok(machine.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
