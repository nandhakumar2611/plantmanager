package com.example.plantmanager.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.plantmanager.model.User;

/**
* UserDetailsImpl class that implements UserDetails interface. 
* <p>
* If the authentication process is successful, we can get User’s information 
* such as username, password, authorities from an Authentication object.
* If we want to get more data (id, email…), we can create an implementation of this UserDetails interface.
* </p>
*
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String username;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long userId, String username, String password, String email, 
			Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getPassword(), 
				user.getEmail(), 
				authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public Long getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
