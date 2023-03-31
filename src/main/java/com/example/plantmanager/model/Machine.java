package com.example.plantmanager.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "machines")
public class Machine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "machine_id")
	private Long id;
	
	@Column(name = "machine_name")
	private String machineName;
	
	@Column(name = "machien_desc")
	private String machineDesc;

	@ManyToMany(fetch = FetchType.LAZY, 
				cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name = "machine_operation", 
				joinColumns = {@JoinColumn(name="machine_id")},
				inverseJoinColumns = {@JoinColumn(name = "operation_id")})
	private Set<Operation> operations = new HashSet<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineDesc() {
		return machineDesc;
	}

	public void setMachineDesc(String machineDesc) {
		this.machineDesc = machineDesc;
	}

	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Machine(String machineName, String machineDesc) {
		this.machineName = machineName;
		this.machineDesc = machineDesc;
	}

	public Machine() {
	}
	
	public void addOperation(Operation operation) {
		this.operations.add(operation);
		operation.getMachines().add(this);
	}
	
	public void removeOperation(Long operationid) {
		Operation operation = this.operations.stream().filter(operationdelete -> operationdelete.getId() == operationid).findFirst().orElse(null);
		if(operation != null) {
			this.operations.remove(operation);
			operation.getMachines().remove(this);
		}
	}
}
