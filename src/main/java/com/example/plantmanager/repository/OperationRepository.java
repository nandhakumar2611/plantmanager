package com.example.plantmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plantmanager.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{

}
