package com.example.plantmanager.service;

import java.util.List;
import java.util.Optional;

import com.example.plantmanager.model.Machine;

public interface MachineService {

	Machine saveMachine(Machine machine);
	
	List<Machine> getAllMachine();
	
	Optional<Machine> findById(Long id);
	
	void deleteById(Long id);
	
	List<Machine> findMachinesByOperationsId(Long id);
	
	Boolean existsById(Long id);
	
	Machine findByMachineName(String machinename);
	
}
