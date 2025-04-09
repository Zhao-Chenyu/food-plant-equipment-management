package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "production_line")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "factory_id", nullable = false)
    private Factory factory;
}
