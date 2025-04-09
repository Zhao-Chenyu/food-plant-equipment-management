package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.ToolCategoryProduction;
import com.example.equipmentsmanagement.repository.ToolCategoryProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToolCategoryProductionService {

    @Autowired
    private ToolCategoryProductionRepository repository;

    public ToolCategoryProduction findOrCreate(Long categoryId, Long factoryId, Long lineId, Long processId) {
        Optional<ToolCategoryProduction> existing = repository
                .findByToolCategoryIdAndFactoryIdAndProductionLineIdAndProcessId(
                        categoryId, factoryId, lineId, processId);

        if (existing.isPresent()) {
            return existing.get();
        }

        ToolCategoryProduction newOne = new ToolCategoryProduction();
        newOne.setToolCategoryId(categoryId);
        newOne.setFactoryId(factoryId);
        newOne.setProductionLineId(lineId);
        newOne.setProcessId(processId);
        return repository.save(newOne);
    }
}
