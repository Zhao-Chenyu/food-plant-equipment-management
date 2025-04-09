package com.example.equipmentsmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tool_category_production")
public class ToolCategoryProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tool Category ID
    @Column(name = "tool_category_id", nullable = false)
    private Long toolCategoryId;

    // Factory ID
    @Column(name = "factory_id", nullable = false)
    private Long factoryId;

    // Production Line ID
    @Column(name = "production_line_id", nullable = false)
    private Long productionLineId;

    // Process ID
    @Column(name = "process_id", nullable = false)
    private Long processId;

    // Creation Time
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    // Update Time
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_category_id", insertable = false, updatable = false)
    private ToolCategory toolCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factory_id", insertable = false, updatable = false)
    private Factory factory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_line_id", insertable = false, updatable = false)
    private ProductionLine productionLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", insertable = false, updatable = false)
    private ManufacturingProcess process;
}
