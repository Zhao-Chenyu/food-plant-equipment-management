package com.example.equipmentsmanagement.service;

import com.example.equipmentsmanagement.model.Factory;
import com.example.equipmentsmanagement.repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryService {
    private final FactoryRepository factoryRepository;

    public List<Factory> getAllFactories() {
        return factoryRepository.findAll();
    }

    public Factory createFactory(Factory factory) {
        return factoryRepository.save(factory);
    }

    public void deleteFactory(Long id) {
        factoryRepository.deleteById(id);
    }
}
