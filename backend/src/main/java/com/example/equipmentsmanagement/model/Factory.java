package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "factory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String location;
}
