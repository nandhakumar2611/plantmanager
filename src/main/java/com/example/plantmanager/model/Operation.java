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
* Operation entity to represent a Operation of the {@link Machine} in the system.
* 
* @author Nandha Kumar
* @version 1.0
* @since 24-03-2023
* 
*/

@Entity
@Table(name = "operation")
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operation_id")
	private Long id;
	
	@Column(name = "operation_name")
	private String operationName;

	@Column(name = "operation_desc")
	private String operationDesc;

	@ManyToMany(mappedBy = "operations", fetch = FetchType.LAZY)
	private Set<Machine> machine;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public Set<Machine> getMachine() {
		return machine;
	}

	public void setMachine(Set<Machine> machine) {
		this.machine = machine;
	}

	public Operation() {
		super();
	}
	
}
