package com.example.plantmanager.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
* Role entity to represent a ROLE of the {@link User} in the system.
* 
* This table holds the user's role. For example, Administrator, User e.t.c
* 
* The JPA definitions of {@link User} and {@link Role} will cause the following
* 3 tables to be created:
* <ul>
* <li>user</li>
* <li>role</li>
* <li>user_roles</li>
* </ul>
* 
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
	private int id;
    
    @Column(name = "role")
	private String role;
    
    /**
     * Defining the Many-to-Many relation of users and roles. A Role can belong
     * to many Users and many Users can belong to a Role.
     */
    
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<User> users;
	  
    // Getters and setters
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Role() {
		super();
	}

}
