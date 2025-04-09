package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.ManufacturingProcess;
import com.example.equipmentsmanagement.model.ProductionLine;
import com.example.equipmentsmanagement.repository.ManufacturingProcessRepository;
import com.example.equipmentsmanagement.repository.ProductionLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturingProcessService {
    private final ManufacturingProcessRepository processRepository;
    private final ProductionLineRepository productionLineRepository;

    public List<ManufacturingProcess> getAllProcesses() {
        return processRepository.findAll();
    }

    public List<ManufacturingProcess> getProcessesByProductionLine(Long productionLineId) { // ✅ 这个方法必须存在
        return processRepository.findByProductionLineId(productionLineId);
    }

    public ManufacturingProcess createProcess(Long productionLineId, ManufacturingProcess process) {
        Optional<ProductionLine> productionLine = productionLineRepository.findById(productionLineId);
        if (productionLine.isPresent()) {
            process.setProductionLine(productionLine.get());
            return processRepository.save(process);
        } else {
            throw new RuntimeException("Production Line not found");
        }
    }

    public void deleteProcess(Long id) { // ✅ 这个方法必须存在
        processRepository.deleteById(id);
    }
}
