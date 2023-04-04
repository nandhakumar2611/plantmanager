package com.example.plantmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantmanager.model.RawMaterial;
import com.example.plantmanager.service.RawMaterialService;

/**
 * Rest Controller to handle all requests to the @user end point (RawMaterial Controller)
 * 
 * @author Nandha Kumar
 * @version 1.0
 * @since March 2023
 * 
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/rawmaterial")
@RestController
public class RawMaterialController {
	
	@Autowired
	private RawMaterialService rawMaterialService;
	
	/**
	 * Create New RawMaterial
	 * 
	 * @param rawMaterial
	 * @return
	 */
	@PostMapping("/rawmaterial")
	public ResponseEntity<RawMaterial> createRawMaterial(@RequestBody RawMaterial rawMaterial) {
		return ResponseEntity.ok(rawMaterialService.saveRawMaterial(rawMaterial));
	}
	
	/**
	 * Get All RawMaterial List
	 * @return
	 */
	@GetMapping("/rawmaterial")
	public ResponseEntity<List<RawMaterial>> getAllRawMaterial() {
		
		List<RawMaterial> rawMaterials = new ArrayList<RawMaterial>();
		
		rawMaterialService.getAllRawMaterial().forEach(rawMaterials::add);
		
		if(rawMaterials.isEmpty()) {
			return new ResponseEntity<List<RawMaterial>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<RawMaterial>>(rawMaterials,HttpStatus.OK);
	}
	
	/**
	 * Get RawMaterial By Id
	 * @param id
	 * @return
	 */
	@GetMapping("/rawmaterial/{id}")
	public ResponseEntity<RawMaterial> getMachineById(@PathVariable Long id) {
		Optional<RawMaterial> rawMaterial = rawMaterialService.findById(id);
		
		if(rawMaterial.isPresent()) {
			return new ResponseEntity<RawMaterial>(rawMaterial.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<RawMaterial>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Update RawMaterial By Id
	 * @param id
	 * @param rawMaterialdata
	 * @return
	 */
	@PutMapping("/rawmaterial/{id}")
	public ResponseEntity<RawMaterial> getMachineById(@PathVariable Long id,@RequestBody RawMaterial rawMaterialdata) {
		Optional<RawMaterial> rawMaterialfind = rawMaterialService.findById(id);
		
		if(rawMaterialfind.isPresent()) {
			
			RawMaterial rawMaterial = rawMaterialfind.get();
			rawMaterial.setRawName(rawMaterialdata.getRawName());
			rawMaterial.setRawCode(rawMaterialdata.getRawCode());
			return new ResponseEntity<RawMaterial>(rawMaterialService.saveRawMaterial(rawMaterial),HttpStatus.OK);
		}
		return new ResponseEntity<RawMaterial>(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Delete RawMaterial By Id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<RawMaterial> deleteRawMaterial(@PathVariable Long id) {
		rawMaterialService.deleteById(id);
		return new ResponseEntity<RawMaterial>(HttpStatus.OK);
	}
}
