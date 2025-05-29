package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.TermsEntity;
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
    public ResponseEntity<TermsEntity> createTermsPolicy(@RequestBody TermsEntity termsEntity) {
        TermsEntity createdPolicy = termsService.createTermsPolicy(termsEntity);
        return ResponseEntity.ok(createdPolicy);
    }

    // Get all terms policies
    @GetMapping("/get-allterms")
    public ResponseEntity<List<TermsEntity>> getAllTermsPolicies() {
        List<TermsEntity> policies = termsService.getAllTermsPolicies();
        return ResponseEntity.ok(policies);
    }

    // Get terms policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<TermsEntity> getTermsPolicyById(@PathVariable Long id) {
        TermsEntity policy = termsService.getTermsPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        }
        return ResponseEntity.notFound().build();
    }

    // Update terms policy
    @PutMapping("/{id}")
    public ResponseEntity<TermsEntity> updateTermsPolicy(@PathVariable Long id, @RequestBody TermsEntity termsEntity) {
        TermsEntity updatedPolicy = termsService.updateTermsPolicy(id, termsEntity);
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
    public ResponseEntity<List<TermsEntity>> searchByTitle(@RequestParam String title) {
        List<TermsEntity> policies = termsService.searchByTitle(title);
        return ResponseEntity.ok(policies);
    }
}
