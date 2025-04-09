package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.model.ProductionLine;
import com.example.equipmentsmanagement.service.ProductionLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-lines")
@RequiredArgsConstructor
public class ProductionLineController {
    private final ProductionLineService productionLineService;

    @GetMapping
    public ResponseEntity<List<ProductionLine>> getAllProductionLines() {
        return ResponseEntity.ok(productionLineService.getAllProductionLines());
    }

    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<ProductionLine>> getProductionLinesByFactory(@PathVariable Long factoryId) {
        return ResponseEntity.ok(productionLineService.getProductionLinesByFactory(factoryId));
    }

    @PostMapping("/{factoryId}")
    public ResponseEntity<ProductionLine> createProductionLine(
            @PathVariable Long factoryId,
            @RequestBody ProductionLine productionLine) {
        return ResponseEntity.ok(productionLineService.createProductionLine(factoryId, productionLine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductionLine(@PathVariable Long id) {
        productionLineService.deleteProductionLine(id);
        return ResponseEntity.noContent().build();
    }
}
