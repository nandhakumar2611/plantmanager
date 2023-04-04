package com.example.plantmanager.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nandha Kumar
 * @version 1.0
 * @since March 2023
 *
 */

@CrossOrigin(origins = "*" ,maxAge = 3600)
@RestController
@RequestMapping("/api/authorize")
public class AuthorizeController {

	@GetMapping("/all")
	public String allAccess() {
		return "ALL AUTHORIZED";
	}
	
}
