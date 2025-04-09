package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.AttachmentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttachmentAssignmentRepository extends JpaRepository<AttachmentAssignment, Long> {
    List<AttachmentAssignment> findByAttachmentId(Long attachmentId);
}
