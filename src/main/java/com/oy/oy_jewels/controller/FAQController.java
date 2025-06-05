package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.FAQRequestDTO;
import com.oy.oy_jewels.dto.response.ApiResponse;
import com.oy.oy_jewels.dto.response.FAQResponseDTO;
import com.oy.oy_jewels.entity.FAQEntity;
import com.oy.oy_jewels.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/faq")
@CrossOrigin(origins = "*")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<FAQResponseDTO>> createFAQ(@RequestBody FAQRequestDTO faqRequestDTO) {
        try {
            FAQEntity faqEntity = convertToEntity(faqRequestDTO); // Pass the DTO, not entity
            FAQEntity createdFAQ = faqService.createFAQ(faqEntity);
            FAQResponseDTO response = convertToResponseDTO(createdFAQ);
            return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Failed to create FAQ"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FAQResponseDTO>> getFAQById(@PathVariable Long id) {
        try {
            FAQEntity faq = faqService.getFAQById(id);
            if (faq != null) {
                FAQResponseDTO response = convertToResponseDTO(faq);
                return new ResponseEntity<>(ApiResponse.success("FAQ retrieved successfully", response), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("FAQ not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving FAQ: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FAQResponseDTO>>> getAllFAQs() {
        try {
            List<FAQEntity> faqs = faqService.getAllFAQs();
            List<FAQResponseDTO> responseList = faqs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success("FAQs retrieved successfully", responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving FAQs: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<FAQResponseDTO>> updateFAQ(@PathVariable Long id, @RequestBody FAQRequestDTO faqRequestDTO) {
        try {
            FAQEntity faqEntity = convertToEntity(faqRequestDTO);
            FAQEntity updatedFAQ = faqService.updateFAQ(id, faqEntity);

            if (updatedFAQ != null) {
                FAQResponseDTO response = convertToResponseDTO(updatedFAQ);
                return new ResponseEntity<>(ApiResponse.success("FAQ updated successfully", response), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("FAQ not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error updating FAQ: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFAQ(@PathVariable Long id) {
        try {
            boolean deleted = faqService.deleteFAQ(id);
            if (deleted) {
                return new ResponseEntity<>(ApiResponse.success("FAQ deleted successfully", "Deleted"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("FAQ not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error deleting FAQ: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper methods for conversion
    private FAQEntity convertToEntity(FAQRequestDTO dto) {
        FAQEntity entity = new FAQEntity();
        entity.setFaq1Title(dto.getFaq1Title());
        entity.setFaq1Description(dto.getFaq1Description());
        entity.setFaq2Title(dto.getFaq2Title());
        entity.setFaq2Description(dto.getFaq2Description());
        entity.setFaq3Title(dto.getFaq3Title());
        entity.setFaq3Description(dto.getFaq3Description());
        entity.setFaq4Title(dto.getFaq4Title());
        entity.setFaq4Description(dto.getFaq4Description());
        entity.setFaq5Title(dto.getFaq5Title());
        entity.setFaq5Description(dto.getFaq5Description());
        entity.setFaq6Title(dto.getFaq6Title());
        entity.setFaq6Description(dto.getFaq6Description());
        return entity;
    }

    private FAQResponseDTO convertToResponseDTO(FAQEntity entity) {
        return new FAQResponseDTO(
                entity.getId(),
                entity.getFaq1Title(),
                entity.getFaq1Description(),
                entity.getFaq2Title(),
                entity.getFaq2Description(),
                entity.getFaq3Title(),
                entity.getFaq3Description(),
                entity.getFaq4Title(),
                entity.getFaq4Description(),
                entity.getFaq5Title(),
                entity.getFaq5Description(),
                entity.getFaq6Title(),
                entity.getFaq6Description()
        );
    }
}