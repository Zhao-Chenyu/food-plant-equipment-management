package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.AttachmentHistory;
import com.example.equipmentsmanagement.repository.AttachmentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentHistoryService {

    @Autowired
    private AttachmentHistoryRepository attachmentHistoryRepository;

    // Get attachment history records by attachment ID
    public List<AttachmentHistory> getHistoryByAttachmentId(Long attachmentId) {
        return attachmentHistoryRepository.findByAttachmentId(attachmentId);
    }

    // Save attachment history record
    public AttachmentHistory saveHistory(AttachmentHistory attachmentHistory) {
        return attachmentHistoryRepository.save(attachmentHistory);
    }
}
