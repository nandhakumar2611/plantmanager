package com.example.plantmanager.dto;

/**
 * LoginRequest entity to represent a {@link User} login details to the system
 * 
 * @author Nandha Kumar
 * @version 1.0
 * @since 14-03-2023
 * 
 */

public class LoginRequest {

	private String username;
	
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
