package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.ManufacturingProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<ManufacturingProcess, Long> {
    ManufacturingProcess findByName(String name);
}
