package com.example.plantmanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.Role;
import com.example.plantmanager.repository.RoleRepository;
import com.example.plantmanager.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<Role> getAllRole() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findByRole(String role) {
		return roleRepository.findByRole(role);
	}

}
