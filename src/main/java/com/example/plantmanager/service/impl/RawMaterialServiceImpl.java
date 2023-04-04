package com.example.plantmanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plantmanager.model.RawMaterial;
import com.example.plantmanager.repository.RawMaterialRepository;
import com.example.plantmanager.service.RawMaterialService;

@Service
public class RawMaterialServiceImpl implements RawMaterialService{

	@Autowired
	private RawMaterialRepository rawMaterialRepository;
	
	@Override
	public RawMaterial saveRawMaterial(RawMaterial rawMaterial) {
		return rawMaterialRepository.save(rawMaterial);
	}

	@Override
	public List<RawMaterial> getAllRawMaterial() {
		return rawMaterialRepository.findAll();
	}

	@Override
	public Optional<RawMaterial> findById(Long id) {
		return rawMaterialRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		rawMaterialRepository.deleteById(id);
	}

}
