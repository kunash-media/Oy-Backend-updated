package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CancellationRequestDTO;
import com.oy.oy_jewels.dto.response.CancellationResponseDTO;
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
    public ResponseEntity<CancellationResponseDTO> createCancellationPolicy(@RequestBody CancellationRequestDTO cancellationRequestDTO) {
        CancellationResponseDTO createdPolicy = cancellationService.createCancellationPolicy(cancellationRequestDTO);
        return ResponseEntity.ok(createdPolicy);
    }

    // Get all cancellation policies
    @GetMapping("/get-All-cancellation")
    public ResponseEntity<List<CancellationResponseDTO>> getAllCancellationPolicies() {
        List<CancellationResponseDTO> policies = cancellationService.getAllCancellationPolicies();
        return ResponseEntity.ok(policies);
    }

    // Get cancellation policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<CancellationResponseDTO> getCancellationPolicyById(@PathVariable Long id) {
        CancellationResponseDTO policy = cancellationService.getCancellationPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        }
        return ResponseEntity.notFound().build();
    }

    // Update cancellation policy
    @PutMapping("/{id}")
    public ResponseEntity<CancellationResponseDTO> updateCancellationPolicy(@PathVariable Long id, @RequestBody CancellationRequestDTO cancellationRequestDTO) {
        CancellationResponseDTO updatedPolicy = cancellationService.updateCancellationPolicy(id, cancellationRequestDTO);
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
    public ResponseEntity<List<CancellationResponseDTO>> searchByTitle(@RequestParam String title) {
        List<CancellationResponseDTO> policies = cancellationService.searchByTitle(title);
        return ResponseEntity.ok(policies);
    }
}
