package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tool_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
