package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.FAQEntity;
import com.oy.oy_jewels.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @PostMapping("/create")
    public ResponseEntity<FAQEntity> createFAQ(@RequestBody FAQEntity faqEntity) {
        FAQEntity createdFAQ = faqService.createFAQ(FAQEntity);
        return ResponseEntity.ok(createdFAQ);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQEntity> getFAQById(@PathVariable Long id) {
        FAQEntity faq = faqService.getFAQById(id);
        if (faq != null) {
            return ResponseEntity.ok(faq);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<FAQEntity>> getAllFAQs() {
        List<FAQEntity> faqs = faqService.getAllFAQs();
        return ResponseEntity.ok(faqs);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FAQEntity> updateFAQ(@PathVariable Long id, @RequestBody FAQEntity faqEntity) {
        FAQEntity updatedFAQ = faqService.updateFAQ(id, faqEntity);
        if (updatedFAQ != null) {
            return ResponseEntity.ok(updatedFAQ);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
        return ResponseEntity.ok().build();
    }
}
