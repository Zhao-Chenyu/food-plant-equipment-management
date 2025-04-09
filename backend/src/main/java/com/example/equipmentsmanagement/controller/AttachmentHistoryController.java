package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.service.AttachmentHistoryService;
import com.example.equipmentsmanagement.model.AttachmentHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachment-history")
public class AttachmentHistoryController {

    @Autowired
    private AttachmentHistoryService attachmentHistoryService;

    // Get attachment history records
    @GetMapping("/{attachmentId}")
    public ResponseEntity<List<AttachmentHistory>> getHistoryByAttachmentId(@PathVariable Long attachmentId) {
        List<AttachmentHistory> historyList = attachmentHistoryService.getHistoryByAttachmentId(attachmentId);
        return ResponseEntity.ok(historyList);
    }

    // Save history when uploading a new version of an attachment
    @PostMapping("/upload-history")
    public ResponseEntity<String> uploadAttachmentHistory(@RequestBody AttachmentHistory attachmentHistory) {
        attachmentHistoryService.saveHistory(attachmentHistory);
        return ResponseEntity.ok("Attachment history record uploaded successfully");
    }
}
