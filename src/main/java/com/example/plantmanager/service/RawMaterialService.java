package com.example.plantmanager.service;

import java.util.List;
import java.util.Optional;

import com.example.plantmanager.model.RawMaterial;

public interface RawMaterialService {
	
	RawMaterial saveRawMaterial(RawMaterial rawMaterial);
	
	List<RawMaterial> getAllRawMaterial();
	
	Optional<RawMaterial> findById(Long id);
	
	void deleteById(Long id);

}
