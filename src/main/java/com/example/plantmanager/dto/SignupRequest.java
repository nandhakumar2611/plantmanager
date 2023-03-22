package com.example.plantmanager.dto;

import java.util.Set;

import com.example.plantmanager.model.User;

/**
 * LoginRequest entity to represent a {@link User} SignUp details to the system
 * 
 * @author Nandha Kumar
 * @version 1.0
 * @since 14-03-2023
 * 
 */


public class SignupRequest {

	private String userName;
	private String password;
	private String email;
	private Long contactNo;
	private Set<String> role;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getContactNo() {
		return contactNo;
	}
	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}
	public Set<String> getRole() {
		return role;
	}
	public void setRole(Set<String> role) {
		this.role = role;
	}	
	
}
