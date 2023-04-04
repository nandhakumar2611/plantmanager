package com.example.plantmanager.controller;

/**
 * @author Nandha Kumar
 * @version 1.0
 * @since March 2023
 *
 */

public class MessageResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageResponse(String message) {
		super();
		this.message = message;
	}
	
}
