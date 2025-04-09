package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.Factory;
import com.example.equipmentsmanagement.model.ProductionLine;
import com.example.equipmentsmanagement.repository.FactoryRepository;
import com.example.equipmentsmanagement.repository.ProductionLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductionLineService {
    private final ProductionLineRepository productionLineRepository;
    private final FactoryRepository factoryRepository;

    public List<ProductionLine> getAllProductionLines() {
        return productionLineRepository.findAll();
    }

    public List<ProductionLine> getProductionLinesByFactory(Long factoryId) {
        return productionLineRepository.findByFactoryId(factoryId);
    }

    public ProductionLine createProductionLine(Long factoryId, ProductionLine productionLine) {
        Optional<Factory> factory = factoryRepository.findById(factoryId);
        if (factory.isPresent()) {
            productionLine.setFactory(factory.get());
            return productionLineRepository.save(productionLine);
        } else {
            throw new RuntimeException("Factory not found");
        }
    }

    public void deleteProductionLine(Long id) {
        productionLineRepository.deleteById(id);
    }
}
