package com.example.plantmanager.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.User;
import com.example.plantmanager.service.UserService;

/**
*
* The {@link UserDetailsServiceImpl} class simply implements the contract and 
* delegates to {@link UserDetailsService} to get the {@link com.example.model.User} 
* from the database so that it can be compared with the {@link org.springframework.security.core.userdetails.User} 
* for authentication. Authentication occurs via the {@link CustomUserDetailsAuthenticationProvider}.
*
* <p>As we know, if we need user details, we need to implement the user details service.</p>
* 
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND WITH USERNAME: " + username));
		
		return UserDetailsImpl.build(user);
	}

}
