package com.example.plantmanager.service;

import java.util.Optional;

import com.example.plantmanager.model.User;

/**
 * Service definition which accesses the {@link User} entity. This is
 * the recommended way to access the entities through an interface rather than
 * using the corresponding repository. This allows for separation into
 * repository code and the service layer.
 *
 * @author Nandha Kumar
 * @version 1.0
 * @since 14-03-2023
 * 
 */

public interface UserService {

	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	User saveuser(User user);
}
