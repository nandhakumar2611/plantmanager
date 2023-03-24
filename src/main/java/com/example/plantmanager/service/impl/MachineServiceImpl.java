package com.example.plantmanager.service.impl;

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

}
