package com.example.plantmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plantmanager.model.User;

/**
 * Repository for the {@link User} JPA entity. Any custom methods, not already
 * defined in {@link JpaRepository}, are to be defined here.
 * 
 * @author Nandha Kumar
 * @version 1.0
 * @since 14-03-2023
 * 
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}
