package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.model.Attachment;
import com.example.equipmentsmanagement.model.AttachmentAssignment;
import com.example.equipmentsmanagement.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    /**
     * Get the details of the attachment
     */
    @GetMapping("/{id}")
    public Attachment getAttachmentDetail(@PathVariable Long id) {
        return attachmentService.getAttachmentById(id);
    }

    /**
     * Get the list of assignments (Factory - Production Line - Process)
     */
    @GetMapping("/{id}/assignments")
    public List<AttachmentAssignment> getAttachmentAssignments(@PathVariable Long id) {
        return attachmentService.getAssignmentsByAttachmentId(id);
    }
}
