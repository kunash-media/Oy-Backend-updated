package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.TermsRequestDTO;
import com.oy.oy_jewels.dto.response.TermsResponseDTO;
import com.oy.oy_jewels.service.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
public class TermsController {

    @Autowired
    private TermsService termsService;

    // Create new terms policy
    @PostMapping("/create-terms")
    public ResponseEntity<TermsResponseDTO> createTermsPolicy(@RequestBody TermsRequestDTO termsRequestDTO) {
        TermsResponseDTO createdPolicy = termsService.createTermsPolicy(termsRequestDTO);
        return ResponseEntity.ok(createdPolicy);
    }

    // Get all terms policies
    @GetMapping("/get-allterms")
    public ResponseEntity<List<TermsResponseDTO>> getAllTermsPolicies() {
        List<TermsResponseDTO> policies = termsService.getAllTermsPolicies();
        return ResponseEntity.ok(policies);
    }

    // Get terms policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<TermsResponseDTO> getTermsPolicyById(@PathVariable Long id) {
        TermsResponseDTO policy = termsService.getTermsPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        }
        return ResponseEntity.notFound().build();
    }

    // Update terms policy
    @PutMapping("/{id}")
    public ResponseEntity<TermsResponseDTO> updateTermsPolicy(@PathVariable Long id, @RequestBody TermsRequestDTO termsRequestDTO) {
        TermsResponseDTO updatedPolicy = termsService.updateTermsPolicy(id, termsRequestDTO);
        if (updatedPolicy != null) {
            return ResponseEntity.ok(updatedPolicy);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete terms policy
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermsPolicy(@PathVariable Long id) {
        termsService.deleteTermsPolicy(id);
        return ResponseEntity.noContent().build();
    }

    // Search terms policies by title
    @GetMapping("/search")
    public ResponseEntity<List<TermsResponseDTO>> searchByTitle(@RequestParam String title) {
        List<TermsResponseDTO> policies = termsService.searchByTitle(title);
        return ResponseEntity.ok(policies);
    }
}