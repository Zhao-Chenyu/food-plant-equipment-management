package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "process")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturingProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "production_line_id", nullable = false)
    private ProductionLine productionLine;
}
