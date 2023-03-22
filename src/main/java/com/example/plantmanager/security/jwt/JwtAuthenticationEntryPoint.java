package com.example.plantmanager.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
* JwtAuthenticationEntryPoint class that implements AuthenticationEntryPoint interface. 
* Then we override the commence() method. 
* This method will be triggerd anytime unauthenticated User requests a secured HTTP resource and an AuthenticationException is thrown.
*
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		LOGGER.error("UNAUTHORIZED ERROR: ", authException.getMessage());
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error", "UNAUTHORIZED");
		body.put("message", authException.getMessage());
		body.put("path", request.getServletPath());
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
		
	}

}
