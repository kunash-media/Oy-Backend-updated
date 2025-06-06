package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.FAQRequestDTO;
import com.oy.oy_jewels.dto.response.ApiResponse;
import com.oy.oy_jewels.dto.response.FAQResponseDTO;
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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<FAQResponseDTO>> createFAQ(@RequestBody FAQRequestDTO requestDTO) {
        try {
            FAQResponseDTO responseDTO = faqService.createFAQ(requestDTO);
            if (responseDTO != null) {
                return ResponseEntity.ok(ApiResponse.success("FAQ created successfully", responseDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Failed to create FAQ", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FAQResponseDTO>> getFAQById(@PathVariable Long id) {
        try {
            FAQResponseDTO responseDTO = faqService.getFAQById(id);
            if (responseDTO != null) {
                return ResponseEntity.ok(ApiResponse.success("FAQ retrieved successfully", responseDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("FAQ not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FAQResponseDTO>>> getAllFAQs() {
        try {
            List<FAQResponseDTO> responseDTOs = faqService.getAllFAQs();
            return ResponseEntity.ok(ApiResponse.success("FAQs retrieved successfully", responseDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FAQResponseDTO>> updateFAQ(@PathVariable Long id, @RequestBody FAQRequestDTO requestDTO) {
        try {
            FAQResponseDTO responseDTO = faqService.updateFAQ(id, requestDTO);
            if (responseDTO != null) {
                return ResponseEntity.ok(ApiResponse.success("FAQ updated successfully", responseDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("FAQ not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFAQ(@PathVariable Long id) {
        try {
            boolean deleted = faqService.deleteFAQ(id);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success("FAQ deleted successfully", "FAQ with ID " + id + " has been deleted"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("FAQ not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage(), null));
        }
    }
}
