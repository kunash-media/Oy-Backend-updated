package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.entity.FAQEntity;
import com.oy.oy_jewels.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@CrossOrigin(origins = "*")
public class FAQController {

    @Autowired
    private FAQService faqService;

    // Create new FAQ
    @PostMapping("/create-FAQ")
    public ResponseEntity<FAQEntity> createFAQ(@RequestBody FAQEntity faq) {
        FAQEntity createdFAQ = faqService.createFAQ(faq);
        return new ResponseEntity<>(createdFAQ, HttpStatus.CREATED);
    }

    // Get all active FAQs
    @GetMapping("/get-All-Active-FAQs")
    public ResponseEntity<List<FAQEntity>> getAllActiveFAQs() {
        List<FAQEntity> faqs = faqService.getAllActiveFAQs();
        return new ResponseEntity<>(faqs, HttpStatus.OK);
    }

    // Get FAQ by ID
    @GetMapping("/{id}")
    public ResponseEntity<FAQEntity> getFAQById(@PathVariable Long id) {
        FAQEntity faq = faqService.getFAQById(id);
        if (faq != null) {
            return new ResponseEntity<>(faq, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update FAQ
    @PutMapping("/{id}")
    public ResponseEntity<FAQEntity> updateFAQ(@PathVariable Long id, @RequestBody FAQEntity faq) {
        FAQEntity updatedFAQ = faqService.updateFAQ(id, faq);
        if (updatedFAQ != null) {
            return new ResponseEntity<>(updatedFAQ, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete FAQ (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get FAQs by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<FAQEntity>> getFAQsByCategory(@PathVariable String category) {
        List<FAQEntity> faqs = faqService.getFAQsByCategory(category);
        return new ResponseEntity<>(faqs, HttpStatus.OK);
    }

    // Search FAQs by keyword
    @GetMapping("/search")
    public ResponseEntity<List<FAQEntity>> searchFAQs(@RequestParam String keyword) {
        List<FAQEntity> faqs = faqService.searchFAQs(keyword);
        return new ResponseEntity<>(faqs, HttpStatus.OK);
    }

    // Get all active categories
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllActiveCategories() {
        List<String> categories = faqService.getAllActiveCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Get FAQs by multiple categories
    @PostMapping("/categories")
    public ResponseEntity<List<FAQEntity>> getFAQsByCategories(@RequestBody List<String> categories) {
        List<FAQEntity> faqs = faqService.getFAQsByCategories(categories);
        return new ResponseEntity<>(faqs, HttpStatus.OK);
    }

    // Reorder FAQs
    @PutMapping("/reorder")
    public ResponseEntity<Void> reorderFAQs(@RequestBody List<Long> faqIds) {
        faqService.reorderFAQs(faqIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Toggle FAQ active status
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<FAQEntity> toggleFAQStatus(@PathVariable Long id) {
        FAQEntity faq = faqService.toggleFAQStatus(id);
        if (faq != null) {
            return new ResponseEntity<>(faq, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}