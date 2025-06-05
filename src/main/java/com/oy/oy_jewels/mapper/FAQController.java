package com.oy.oy_jewels.mapper;


import com.oy.oy_jewels.dto.request.FAQRequestDTO;
import com.oy.oy_jewels.dto.response.FAQResponseDTO;
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
    public ResponseEntity<FAQResponseDTO> createFAQ( @RequestBody FAQRequestDTO requestDTO) {
        FAQResponseDTO createdFAQ = faqService.createFAQ(requestDTO);
        return ResponseEntity.ok(createdFAQ);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQResponseDTO> getFAQById(@PathVariable Long id) {
        FAQResponseDTO faq = faqService.getFAQById(id);
        if (faq != null) {
            return ResponseEntity.ok(faq);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<FAQResponseDTO>> getAllFAQs() {
        List<FAQResponseDTO> faqs = faqService.getAllFAQs();
        return ResponseEntity.ok(faqs);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FAQResponseDTO> updateFAQ(@PathVariable Long id,  @RequestBody FAQRequestDTO requestDTO) {
        FAQResponseDTO updatedFAQ = faqService.updateFAQ(id, requestDTO);
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