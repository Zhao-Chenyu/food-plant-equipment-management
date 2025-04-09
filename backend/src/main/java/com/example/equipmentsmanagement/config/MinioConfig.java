package com.example.equipmentsmanagement.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        System.out.println("🧪 MinIO 配置检查:");
        System.out.println(" - endpoint: " + endpoint);
        System.out.println(" - accessKey: " + accessKey);
        System.out.println(" - secretKey: " + secretKey);
        System.out.println(" - bucketName: " + bucketName);

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
