package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.ManufacturingProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturingProcessRepository extends JpaRepository<ManufacturingProcess, Long> {

    // Find a manufacturing process by name and production line ID (for binding)
    Optional<ManufacturingProcess> findByNameAndProductionLineId(String name, Long productionLineId);

    // Find all manufacturing processes by production line ID (for listing)
    List<ManufacturingProcess> findByProductionLineId(Long productionLineId);
}
