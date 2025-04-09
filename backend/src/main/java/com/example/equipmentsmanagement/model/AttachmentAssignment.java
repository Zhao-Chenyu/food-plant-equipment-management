package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "attachment_assignment")
public class AttachmentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attachment_id", insertable = false, updatable = false)
    private Long attachmentId;

    @Column(name = "tool_category_production_id", insertable = false, updatable = false)
    private Long toolCategoryProductionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_category_production_id", nullable = false)
    private ToolCategoryProduction toolCategoryProduction;

    @Column(name = "assigned_time")
    private LocalDateTime assignedTime;

    @PrePersist
    protected void onCreate() {
        this.assignedTime = LocalDateTime.now();
    }
}
