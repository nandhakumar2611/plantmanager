package com.example.plantmanager.dto;

import java.util.Set;

public class MachineRequest {

	private String machineName;
	private String machineDesc;
	private Set<Long> operation;
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
	public Set<Long> getOperation() {
		return operation;
	}
	public void setOperation(Set<Long> operation) {
		this.operation = operation;
	}
	
}
	