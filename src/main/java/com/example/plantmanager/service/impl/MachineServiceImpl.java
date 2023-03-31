package com.example.plantmanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.Machine;
import com.example.plantmanager.repository.MachineRepository;
import com.example.plantmanager.service.MachineService;

@Service
public class MachineServiceImpl implements MachineService{

	@Autowired
	private MachineRepository machineRepository;
	
	@Override
	public Machine saveMachine(Machine machine) {
		return machineRepository.save(machine);
	}

	@Override
	public List<Machine> getAllMachine() {
		return machineRepository.findAll();
	}

	@Override
	public Optional<Machine> findById(Long id) {
		return machineRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		machineRepository.deleteById(id);
	}

	@Override
	public Boolean existsById(Long id) {
		return machineRepository.existsById(id);
	}

	@Override
	public List<Machine> findMachinesByOperationsId(Long id) {
		return machineRepository.findMachinesByOperationsId(id);
	}


}
