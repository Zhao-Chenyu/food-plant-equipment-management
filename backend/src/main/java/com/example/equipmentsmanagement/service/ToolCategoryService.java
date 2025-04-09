package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.ToolCategory;
import com.example.equipmentsmanagement.repository.ToolCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolCategoryService {
    private final ToolCategoryRepository toolCategoryRepository;

    public List<ToolCategory> getAllToolCategories() {
        return toolCategoryRepository.findAll();
    }

    public ToolCategory createToolCategory(ToolCategory toolCategory) {
        return toolCategoryRepository.save(toolCategory);
    }

    public void deleteToolCategory(Long id) {
        toolCategoryRepository.deleteById(id);
    }
}
