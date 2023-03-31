package com.example.plantmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.example.plantmanager.exception.ResourceNotFoundException;
import com.example.plantmanager.model.Machine;
import com.example.plantmanager.model.Operation;
import com.example.plantmanager.service.MachineService;
import com.example.plantmanager.service.OperationService;


@RestController
@RequestMapping("api/v1/machine/")
public class MachineController {
	
	@Autowired
	private MachineService machineService;
	
	@Autowired
	private OperationService operationService;
	
	@PostMapping("/machine")
	public ResponseEntity<Machine> createMachine(@RequestBody Machine machine) {
		return ResponseEntity.ok(machineService.saveMachine(machine));
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
	
	@PostMapping("/machines/{machineid}/operations")
	public ResponseEntity<Operation> addOperation(@PathVariable Long machineid,@RequestBody Operation addoperation) {
		
		Operation operation = machineService.findById(machineid).map(machine -> {
			long operationid = addoperation.getId();
			
			if(operationid != 0L) {
				Operation findoperation = operationService.findById(operationid)
						.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND OPERATIONS WITH ID: "+ operationid));
				machine.addOperation(findoperation);
				machineService.saveMachine(machine);
				return findoperation;
			}
			
			machine.addOperation(addoperation);
			return operationService.saveOperation(addoperation);
		}).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND MACHINE WITH ID: " + machineid ));
		
		return new ResponseEntity<>(operation,HttpStatus.CREATED);
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
	
	@DeleteMapping("/machines/{machineid}/operations/{operationid}")
	public ResponseEntity<?> deleteOperationFromMachine(@PathVariable Long machineid, @PathVariable Long operationid) {
		
		Machine machine = machineService.findById(machineid)
				.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND MACHINE WITH ID: " + machineid));
		
		machine.removeOperation(operationid);
		machineService.saveMachine(machine);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{machineid}/operation/{operationid}")
	public Machine assignOperationToMachine(@PathVariable Long machineid,@PathVariable Long operationid) {
		
		Set<Operation> operationset = null;
		Machine machine = machineService.findById(machineid).get();
		Operation operation = operationService.findById(operationid).get();
		operationset = machine.getOperations();
		operationset.add(operation);
		machine.setOperations(operationset);
		return machineService.saveMachine(machine);
	}
	
	
}