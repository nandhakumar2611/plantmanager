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
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	
	@Column(name = "pro_name")
	private String productName;
	
	@Column(name = "pro_desc")
	private String productDesc;
	
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name = "product_rawmaterial",
				joinColumns = {@JoinColumn(name = "product_id")},
				inverseJoinColumns = {@JoinColumn(name = "rawmaterial_id")})
	private Set<RawMaterial> rawMaterials = new HashSet<>();
	

	@ManyToMany(fetch = FetchType.LAZY, 
				cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name = "product_operation", 
				joinColumns = {@JoinColumn(name="product_id")},
				inverseJoinColumns = {@JoinColumn(name = "operation_id")})
	private Set<Operation> operations = new HashSet<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Set<RawMaterial> getRawMaterials() {
		return rawMaterials;
	}

	public void setRawMaterials(Set<RawMaterial> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}

	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Product(String productName, String productDesc) {
		this.productName = productName;
		this.productDesc = productDesc;
	}

	public Product() {
	}
	
}
