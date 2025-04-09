package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // File type (foreign key)
    @ManyToOne
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileType fileType;

    // Attachment name
    @Column(nullable = false)
    private String fileName;

    // Attachment path
    @Column(nullable = false)
    private String filePath;

    // Uploader (foreign key)
    @ManyToOne
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;

    // Upload time
    @Column(nullable = false)
    private LocalDateTime uploadTime = LocalDateTime.now();

    // Attachment version
    private Integer version = 1;

    // Is deprecated
    @Column(name = "is_deprecated")
    private Boolean deprecated = false;

    // Attachment remarks
    private String remark;

    // Parent attachment (for historical version)
    @ManyToOne
    @JoinColumn(name = "parent_attachment_id")
    private Attachment parentAttachment;

    // Set uploader ID
    public void setUploaderId(Long uploaderId) {
        User user = new User();
        user.setId(uploaderId);
        this.uploader = user;
    }

    // Get uploader ID
    public Long getUploaderId() {
        return this.uploader != null ? this.uploader.getId() : null;
    }

    // Set file type ID
    public void setFileTypeId(Long fileTypeId) {
        FileType ft = new FileType();
        ft.setId(fileTypeId);
        this.fileType = ft;
    }

    // Get file type ID
    public Long getFileTypeId() {
        return this.fileType != null ? this.fileType.getId() : null;
    }
}
