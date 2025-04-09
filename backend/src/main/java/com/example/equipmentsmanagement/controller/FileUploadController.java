package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.service.AttachmentService;
import com.example.equipmentsmanagement.storage.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final MinioService minioService;
    private final AttachmentService attachmentService;

    public FileUploadController(MinioService minioService, AttachmentService attachmentService) {
        this.minioService = minioService;
        this.attachmentService = attachmentService;
    }

    // Handle file upload
    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(
            @RequestHeader("username") String username,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("remark") String remark,
            @RequestParam("operations") String operations,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // Decode the username from URL encoding
            String decodedUsername = URLDecoder.decode(username, StandardCharsets.UTF_8);
            List<String> operationList = Arrays.asList(operations.split(","));

            // Log the received upload request
            System.out.println("✅ Upload request received");
            System.out.println("Username: " + decodedUsername);
            System.out.println("Category: " + categoryName);
            System.out.println("Remark: " + remark);
            System.out.println("Operations: " + operations);
            System.out.println("File Name: " + file.getOriginalFilename());

            // ✅ Handle upload and write to database
            attachmentService.handleUpload(categoryName, remark, file, operationList, decodedUsername);

            return ResponseEntity.ok(Map.of("message", "Upload successful"));
        } catch (Exception e) {
            // Log the error and return failure response
            System.err.println("❌ Upload failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }
}
