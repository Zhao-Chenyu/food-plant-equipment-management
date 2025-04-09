package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.ToolCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolCategoryRepository extends JpaRepository<ToolCategory, Long> {
    Optional<ToolCategory> findByName(String name);
}
