package com.example.equipmentsmanagement.repository;

import com.example.equipmentsmanagement.model.AttachmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentHistoryRepository extends JpaRepository<AttachmentHistory, Long> {

    // Find all history records for a specific attachment
    List<AttachmentHistory> findByAttachmentId(Long attachmentId);

    // Find and order the history records by version in descending order
    List<AttachmentHistory> findByAttachmentIdOrderByVersionDesc(Long attachmentId);
}
