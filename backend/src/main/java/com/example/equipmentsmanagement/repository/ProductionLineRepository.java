package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.ProductionLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLine, Long> {

    Optional<ProductionLine> findByNameAndFactoryId(String name, Long factoryId);

    List<ProductionLine> findByFactoryId(Long factoryId);
}
