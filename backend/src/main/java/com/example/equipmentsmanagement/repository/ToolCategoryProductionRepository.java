package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.ToolCategoryProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolCategoryProductionRepository extends JpaRepository<ToolCategoryProduction, Long> {

    Optional<ToolCategoryProduction> findByToolCategoryIdAndFactoryIdAndProductionLineIdAndProcessId(
            Long toolCategoryId,
            Long factoryId,
            Long productionLineId,
            Long processId
    );
}
