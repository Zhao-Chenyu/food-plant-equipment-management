package com.example.equipmentsmanagement;

import com.example.equipmentsmanagement.config.MinioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication

public class EquipmentsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentsManagementApplication.class, args);
    }
}
