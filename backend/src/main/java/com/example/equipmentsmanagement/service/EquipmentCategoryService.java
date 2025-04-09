package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.EquipmentCategory;
import com.example.equipmentsmanagement.repository.EquipmentCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentCategoryService {

    private final EquipmentCategoryRepository equipmentCategoryRepository;

    public EquipmentCategoryService(EquipmentCategoryRepository equipmentCategoryRepository) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;
    }

    public List<EquipmentCategory> getAllCategories() {
        return equipmentCategoryRepository.findAll();
    }
}
