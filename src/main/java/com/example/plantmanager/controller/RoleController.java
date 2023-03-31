package com.example.plantmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantmanager.model.Role;
import com.example.plantmanager.service.RoleService;

@RestController
@RequestMapping("/api/v1/role/")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/role")
	public ResponseEntity<List<Role>> getAllRole() {
		
		List<Role> role = new ArrayList<Role>();
		
		roleService.getAllRole().forEach(role::add);
		
		if(role.isEmpty()) {
			return new ResponseEntity<List<Role>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Role>>(role,HttpStatus.OK);
	}
}
