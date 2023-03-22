package com.example.plantmanager.service;

import java.util.Optional;

import com.example.plantmanager.model.Role;

public interface RoleService {

	Optional<Role> findByRole(String role);
}
