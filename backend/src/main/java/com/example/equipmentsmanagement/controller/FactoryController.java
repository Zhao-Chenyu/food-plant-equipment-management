package com.example.equipmentsmanagement.controller;

import com.example.equipmentsmanagement.model.Factory;
import com.example.equipmentsmanagement.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factories")
@RequiredArgsConstructor
public class FactoryController {
    private final FactoryService factoryService;

    @GetMapping
    public ResponseEntity<List<Factory>> getAllFactories() {
        return ResponseEntity.ok(factoryService.getAllFactories());
    }

    @PostMapping
    public ResponseEntity<Factory> createFactory(@RequestBody Factory factory) {
        return ResponseEntity.ok(factoryService.createFactory(factory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Long id) {
        factoryService.deleteFactory(id);
        return ResponseEntity.noContent().build();
    }
}
