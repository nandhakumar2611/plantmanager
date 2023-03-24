package com.example.plantmanager.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@RestController
@RequestMapping("/api/authorize")
public class AuthorizeController {

	@GetMapping("/all")
	public String allAccess() {
		return "ALL AUTHORIZED";
	}
	
	@GetMapping("/admin")
	public String adminAccess() {
		return "ADMIN AUTHORIZED";
	}
	
	@GetMapping("/user")
	public String userAccess() {
		return "USER AUTHORIZED";
	}
	
	@GetMapping("/manager")
	public String managerAccess() {
		return "MANAGER AUTHORIZED";
	}
}