package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.DisclaimerEntity;
import com.oy.oy_jewels.service.DisclaimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disclaimer")
public class DisclaimerController {

    @Autowired
    private DisclaimerService disclaimerService;

    // Create new disclaimer
    @PostMapping("/create-Disclaimer")
    public ResponseEntity<DisclaimerEntity> createDisclaimer(@RequestBody DisclaimerEntity disclaimerEntity) {
        DisclaimerEntity createdDisclaimer = disclaimerService.createDisclaimer(disclaimerEntity);
        return ResponseEntity.ok(createdDisclaimer);
    }

    // Get all disclaimers
    @GetMapping("/get-all-disclaimers")
    public ResponseEntity<List<DisclaimerEntity>> getAllDisclaimers() {
        List<DisclaimerEntity> disclaimers = disclaimerService.getAllDisclaimers();
        return ResponseEntity.ok(disclaimers);
    }

    // Get disclaimer by ID
    @GetMapping("/{id}")
    public ResponseEntity<DisclaimerEntity> getDisclaimerById(@PathVariable Long id) {
        DisclaimerEntity disclaimer = disclaimerService.getDisclaimerById(id);
        if (disclaimer != null) {
            return ResponseEntity.ok(disclaimer);
        }
        return ResponseEntity.notFound().build();
    }

    // Update disclaimer
    @PutMapping("/{id}")
    public ResponseEntity<DisclaimerEntity> updateDisclaimer(@PathVariable Long id, @RequestBody DisclaimerEntity disclaimerEntity) {
        DisclaimerEntity updatedDisclaimer = disclaimerService.updateDisclaimer(id, disclaimerEntity);
        if (updatedDisclaimer != null) {
            return ResponseEntity.ok(updatedDisclaimer);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete disclaimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisclaimer(@PathVariable Long id) {
        disclaimerService.deleteDisclaimer(id);
        return ResponseEntity.noContent().build();
    }

    // Search disclaimers by title
    @GetMapping("/search")
    public ResponseEntity<List<DisclaimerEntity>> searchByTitle(@RequestParam String title) {
        List<DisclaimerEntity> disclaimers = disclaimerService.searchByTitle(title);
        return ResponseEntity.ok(disclaimers);
    }

    // Get disclaimer by section number
    @GetMapping("/section/{sectionNumber}")
    public ResponseEntity<DisclaimerEntity> getDisclaimerBySectionNumber(@PathVariable Integer sectionNumber) {
        DisclaimerEntity disclaimer = disclaimerService.getDisclaimerBySectionNumber(sectionNumber);
        if (disclaimer != null) {
            return ResponseEntity.ok(disclaimer);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all disclaimers ordered by section number
    @GetMapping("/ordered")
    public ResponseEntity<List<DisclaimerEntity>> getAllDisclaimersOrdered() {
        List<DisclaimerEntity> disclaimers = disclaimerService.getAllDisclaimersOrdered();
        return ResponseEntity.ok(disclaimers);
    }

    // Get disclaimers without titles
    @GetMapping("/no-titles")
    public ResponseEntity<List<DisclaimerEntity>> getDisclaimersWithoutTitles() {
        List<DisclaimerEntity> disclaimers = disclaimerService.getDisclaimersWithoutTitles();
        return ResponseEntity.ok(disclaimers);
    }
}
