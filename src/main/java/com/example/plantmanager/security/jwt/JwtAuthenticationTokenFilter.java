package com.example.plantmanager.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.plantmanager.service.impl.UserDetailsServiceImpl;

/**
* AuthTokenFilter class that extends OncePerRequestFilter and override doFilterInternal() method.
* 
* <p>
* OncePerRequestFilter makes a single execution for each request to our API.
* It provides a doFilterInternal() method that we will implement parsing & validating JWT, 
* loading User details (using UserDetailsService), checking Authorization (using UsernamePasswordAuthenticationToken)
* </p>
*
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwtToken = parseJwt(request);
			if(jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
				String username = jwtUtils.getUserNameFromJwtToKen(jwtToken);
				
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		catch (Exception ex) {
			LOGGER.error("CANNOT SET USER AUTHENTICATION : ", ex);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {

		String headerAuthentication = request.getHeader("Authorization");
		
		if(StringUtils.hasText(headerAuthentication) && headerAuthentication.startsWith("Bearer ")) {
			return headerAuthentication.substring(7,headerAuthentication.length());
		}
		return null;
	}

}
