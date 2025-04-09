package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipment_categories")
public class EquipmentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Constructor
    public EquipmentCategory() {}

    public EquipmentCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
