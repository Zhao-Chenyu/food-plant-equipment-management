package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.service.ManufacturingProcessService; // ✅ 正确的 Service
import com.example.equipmentsmanagement.model.ManufacturingProcess; // ✅ 这是 model
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processes")
@RequiredArgsConstructor
public class ManufacturingProcessController {
    private final ManufacturingProcessService processService;

    @GetMapping
    public ResponseEntity<List<ManufacturingProcess>> getAllProcesses() {
        return ResponseEntity.ok(processService.getAllProcesses());
    }

    @GetMapping("/production-line/{productionLineId}")
    public ResponseEntity<List<ManufacturingProcess>> getProcessesByProductionLine(@PathVariable Long productionLineId) {
        return ResponseEntity.ok(processService.getProcessesByProductionLine(productionLineId));
    }

    @PostMapping("/{productionLineId}")
    public ResponseEntity<ManufacturingProcess> createProcess(
            @PathVariable Long productionLineId,
            @RequestBody ManufacturingProcess process) {
        return ResponseEntity.ok(processService.createProcess(productionLineId, process));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long id) {
        processService.deleteProcess(id);
        return ResponseEntity.noContent().build();
    }
}
