package com.example.plantmanager.service;

import java.util.Optional;

import com.example.plantmanager.model.Role;

/**
 * Service definition which accesses the {@link Role} entity. This is
 * the recommended way to access the entities through an interface rather than
 * using the corresponding repository. This allows for separation into
 * repository code and the service layer.
 *
 * @author Nandha Kumar
 * @version 1.0
 * @since 24-03-2023
 * 
 */

public interface RoleService {

	Optional<Role> findByRole(String role);
	
}
