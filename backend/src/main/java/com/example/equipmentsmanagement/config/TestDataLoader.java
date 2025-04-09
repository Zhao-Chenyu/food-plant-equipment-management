package com.example.equipmentsmanagement.config;

import com.example.equipmentsmanagement.model.FileType;
import com.example.equipmentsmanagement.model.User;
import com.example.equipmentsmanagement.repository.FileTypeRepository;
import com.example.equipmentsmanagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @PostConstruct
    public void loadTestData() {
        // Add test user if it does not exist
        if (userRepository.findByUsername("testuser").isEmpty()) {
            User user = new User();
            user.setUsername("testuser");
            user.setEmail("test@example.com");
            user.setPassword("123456");
            user.setRole("admin");
            userRepository.save(user);
            System.out.println("✅ Test user 'testuser' inserted");
        }

        // Add default file type PDF if not already present
        if (fileTypeRepository.findByNameIgnoreCase("pdf").isEmpty()) {
            FileType fileType = new FileType();
            fileType.setName("pdf");
            fileType.setDescription("Portable Document Format");
            fileTypeRepository.save(fileType);
            System.out.println("✅ Default file type 'PDF' inserted");
        }
    }
}
