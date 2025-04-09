package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    // You can add custom query methods here
}
