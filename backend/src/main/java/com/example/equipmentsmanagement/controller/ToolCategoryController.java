package com.example.equipmentsmanagement.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tools")
public class ToolCategoryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Tool category fuzzy search interface
    @GetMapping("/searchCategory")
    public ResponseEntity<List<Map<String, Object>>> searchToolCategories(@RequestParam("name") String name) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(
                "SELECT id, name FROM Tool_Category WHERE name ILIKE ? LIMIT 10",
                "%" + name + "%"  // Search for tool categories that match the given name
        );
        return ResponseEntity.ok(result);
    }
}
