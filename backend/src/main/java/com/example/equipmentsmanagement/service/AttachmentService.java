package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.*;
import com.example.equipmentsmanagement.repository.*;
import com.example.equipmentsmanagement.storage.MinioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentHistoryRepository attachmentHistoryRepository;
    private final AttachmentAssignmentRepository attachmentAssignmentRepository;
    private final ToolCategoryRepository toolCategoryRepository;
    private final ToolCategoryProductionRepository toolCategoryProductionRepository;
    private final FactoryRepository factoryRepository;
    private final ProductionLineRepository productionLineRepository;
    private final ManufacturingProcessRepository processRepository;
    private final FileTypeRepository fileTypeRepository;
    private final MinioService minioService;

    @Transactional
    public void handleUpload(String categoryName, String remark, MultipartFile file,
                             List<String> operations, String username) {

        log.info("🔥 handleUpload started, Category name: {}, User: {}", categoryName, username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        ToolCategory category = toolCategoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    ToolCategory newCat = new ToolCategory();
                    newCat.setName(categoryName);
                    return toolCategoryRepository.save(newCat);
                });

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new RuntimeException("Unrecognized file extension: " + originalFileName);
        }
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

        FileType fileType = fileTypeRepository.findByNameIgnoreCase(extension)
                .orElseThrow(() -> new RuntimeException("File type not defined: " + extension));

        String filePath;
        try {
            filePath = minioService.uploadFile(file);
        } catch (Exception e) {
            log.error("❌ MinIO file upload failed", e);
            throw new RuntimeException("Upload failed, please try again later!");
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(originalFileName);
        attachment.setFilePath(filePath);
        attachment.setUploader(user);
        attachment.setUploadTime(LocalDateTime.now());
        attachment.setRemark(remark);
        attachment.setFileType(fileType);
        attachment.setVersion(1);
        attachment.setDeprecated(false);
        Attachment saved = attachmentRepository.save(attachment);

        AttachmentHistory history = new AttachmentHistory();
        history.setAttachment(saved);
        history.setFileName(originalFileName);
        history.setFilePath(filePath);
        history.setUploader(user);
        history.setVersion(1);
        history.setIsDeprecated(false);
        attachmentHistoryRepository.save(history);

        for (String op : operations) {
            String[] parts = op.split("-");
            if (parts.length != 3) continue;

            String factoryName = parts[0];
            String lineName = parts[1];
            String processName = parts[2];

            Factory factory = factoryRepository.findByName(factoryName)
                    .orElseThrow(() -> new RuntimeException("Factory not found: " + factoryName));

            ProductionLine line = productionLineRepository.findByNameAndFactoryId(lineName, factory.getId())
                    .orElseThrow(() -> new RuntimeException("Production line not found: " + lineName));

            ManufacturingProcess process = processRepository.findByNameAndProductionLineId(processName, line.getId())
                    .orElseThrow(() -> new RuntimeException("Process not found: " + processName));

            ToolCategoryProduction tcp = toolCategoryProductionRepository
                    .findByToolCategoryIdAndFactoryIdAndProductionLineIdAndProcessId(
                            category.getId(), factory.getId(), line.getId(), process.getId())
                    .orElseGet(() -> {
                        ToolCategoryProduction newTcp = new ToolCategoryProduction();
                        newTcp.setToolCategory(category);
                        newTcp.setToolCategoryId(category.getId());
                        newTcp.setFactory(factory);
                        newTcp.setFactoryId(factory.getId());
                        newTcp.setProductionLine(line);
                        newTcp.setProductionLineId(line.getId());
                        newTcp.setProcess(process);
                        newTcp.setProcessId(process.getId());
                        newTcp.setCreateTime(LocalDateTime.now());
                        newTcp.setUpdateTime(LocalDateTime.now());
                        return toolCategoryProductionRepository.save(newTcp);
                    });

            AttachmentAssignment assignment = new AttachmentAssignment();
            assignment.setAttachment(saved);
            assignment.setToolCategoryProduction(tcp);
            attachmentAssignmentRepository.save(assignment);
        }
    }

    // Get attachment by its ID
    public Attachment getAttachmentById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found with id: " + id));
    }

    // Get all assignments related to an attachment
    public List<AttachmentAssignment> getAssignmentsByAttachmentId(Long attachmentId) {
        return attachmentAssignmentRepository.findByAttachmentId(attachmentId);
    }
}
