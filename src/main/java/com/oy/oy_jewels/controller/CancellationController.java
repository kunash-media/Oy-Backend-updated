package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.CancellationEntity;
import com.oy.oy_jewels.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cancellation")
public class CancellationController {

    @Autowired
    private CancellationService cancellationService;

    // Create new cancellation policy
    @PostMapping("/create-cancellation")
    public ResponseEntity<CancellationEntity> createCancellationPolicy(@RequestBody CancellationEntity cancellationEntity) {
        CancellationEntity createdPolicy = cancellationService.createCancellationPolicy(cancellationEntity);
        return ResponseEntity.ok(createdPolicy);
    }

    // Get all cancellation policies
    @GetMapping("/get-All-cancellation")
    public ResponseEntity<List<CancellationEntity>> getAllCancellationPolicies() {
        List<CancellationEntity> policies = cancellationService.getAllCancellationPolicies();
        return ResponseEntity.ok(policies);
    }

    // Get cancellation policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<CancellationEntity> getCancellationPolicyById(@PathVariable Long id) {
        CancellationEntity policy = cancellationService.getCancellationPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        }
        return ResponseEntity.notFound().build();
    }

    // Update cancellation policy
    @PutMapping("/{id}")
    public ResponseEntity<CancellationEntity> updateCancellationPolicy(@PathVariable Long id, @RequestBody CancellationEntity cancellationEntity) {
        CancellationEntity updatedPolicy = cancellationService.updateCancellationPolicy(id, cancellationEntity);
        if (updatedPolicy != null) {
            return ResponseEntity.ok(updatedPolicy);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete cancellation policy
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCancellationPolicy(@PathVariable Long id) {
        cancellationService.deleteCancellationPolicy(id);
        return ResponseEntity.noContent().build();
    }

    // Search cancellation policies by title
    @GetMapping("/search")
    public ResponseEntity<List<CancellationEntity>> searchByTitle(@RequestParam String title) {
        List<CancellationEntity> policies = cancellationService.searchByTitle(title);
        return ResponseEntity.ok(policies);
    }
}
