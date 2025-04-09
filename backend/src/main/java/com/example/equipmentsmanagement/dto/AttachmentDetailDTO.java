package com.example.equipmentsmanagement.dto;

import com.example.equipmentsmanagement.model.Attachment;
import com.example.equipmentsmanagement.model.AttachmentHistory;
import lombok.Data;

import java.util.List;

@Data
public class AttachmentDetailDTO {
    private Attachment attachment;
    private List<String> positions;
    private List<AttachmentHistory> historyList;
}
