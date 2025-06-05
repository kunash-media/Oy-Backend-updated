package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.DisclaimerRequestDTO;
import com.oy.oy_jewels.dto.response.DisclaimerResponseDTO;
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
    public ResponseEntity<DisclaimerResponseDTO> createDisclaimer( @RequestBody DisclaimerRequestDTO requestDTO) {
        DisclaimerResponseDTO createdDisclaimer = disclaimerService.createDisclaimer(requestDTO);
        return ResponseEntity.ok(createdDisclaimer);
    }

    // Get all disclaimers
    @GetMapping("/get-all-disclaimers")
    public ResponseEntity<List<DisclaimerResponseDTO>> getAllDisclaimers() {
        List<DisclaimerResponseDTO> disclaimers = disclaimerService.getAllDisclaimers();
        return ResponseEntity.ok(disclaimers);
    }

    // Get disclaimer by ID
    @GetMapping("/{id}")
    public ResponseEntity<DisclaimerResponseDTO> getDisclaimerById(@PathVariable Long id) {
        DisclaimerResponseDTO disclaimer = disclaimerService.getDisclaimerById(id);
        if (disclaimer != null) {
            return ResponseEntity.ok(disclaimer);
        }
        return ResponseEntity.notFound().build();
    }

    // Update disclaimer
    @PutMapping("/{id}")
    public ResponseEntity<DisclaimerResponseDTO> updateDisclaimer(@PathVariable Long id,@RequestBody DisclaimerRequestDTO requestDTO) {
        DisclaimerResponseDTO updatedDisclaimer = disclaimerService.updateDisclaimer(id, requestDTO);
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
    public ResponseEntity<List<DisclaimerResponseDTO>> searchByTitle(@RequestParam String title) {
        List<DisclaimerResponseDTO> disclaimers = disclaimerService.searchByTitle(title);
        return ResponseEntity.ok(disclaimers);
    }

    // Get disclaimer by section number
    @GetMapping("/section/{sectionNumber}")
    public ResponseEntity<DisclaimerResponseDTO> getDisclaimerBySectionNumber(@PathVariable Integer sectionNumber) {
        DisclaimerResponseDTO disclaimer = disclaimerService.getDisclaimerBySectionNumber(sectionNumber);
        if (disclaimer != null) {
            return ResponseEntity.ok(disclaimer);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all disclaimers ordered by section number
    @GetMapping("/ordered")
    public ResponseEntity<List<DisclaimerResponseDTO>> getAllDisclaimersOrdered() {
        List<DisclaimerResponseDTO> disclaimers = disclaimerService.getAllDisclaimersOrdered();
        return ResponseEntity.ok(disclaimers);
    }

    // Get disclaimers without titles
    @GetMapping("/no-titles")
    public ResponseEntity<List<DisclaimerResponseDTO>> getDisclaimersWithoutTitles() {
        List<DisclaimerResponseDTO> disclaimers = disclaimerService.getDisclaimersWithoutTitles();
        return ResponseEntity.ok(disclaimers);
    }
}