package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.model.EquipmentCategory;
import com.example.equipmentsmanagement.service.EquipmentCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-categories")
public class EquipmentCategoryController {

    private final EquipmentCategoryService equipmentCategoryService;

    // Correct constructor injection
    public EquipmentCategoryController(EquipmentCategoryService equipmentCategoryService) {
        this.equipmentCategoryService = equipmentCategoryService;
    }

    // Get all equipment categories
    @GetMapping
    public List<EquipmentCategory> getAllCategories() {
        return equipmentCategoryService.getAllCategories();
    }
}
