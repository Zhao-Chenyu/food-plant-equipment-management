package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.FileType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileTypeRepository extends JpaRepository<FileType, Long> {

    Optional<FileType> findByNameIgnoreCase(String name);
}
