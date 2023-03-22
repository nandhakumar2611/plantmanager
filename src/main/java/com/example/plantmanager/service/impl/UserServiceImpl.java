package com.example.plantmanager.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.User;
import com.example.plantmanager.repository.UserRepository;
import com.example.plantmanager.service.UserService;

/**
 * Implementation of the {@link UserService} which accesses the
 * {@link User} entity. This is the recommended way to access the
 * entities through an interface rather than using the corresponding repository.
 * This allows for separation into repository code and the service layer.
 *
 * @author Nandha Kumar
 * @version 1.0
 * @since 14-03-2023
 * 
 */

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User saveuser(User user) {
		return userRepository.save(user);
	}

}
