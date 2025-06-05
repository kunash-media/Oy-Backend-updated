package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.response.SEOResponseDTO;
import com.oy.oy_jewels.service.SEOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/seo")
public class SEOController {

    @Autowired
    private SEOService seoService;

    @PostMapping("/create")
    public ResponseEntity<SEOResponseDTO> createSEO(
            @RequestPart(value = "faviconImg", required = false) MultipartFile faviconImg,
            @RequestPart("metaTitle") String metaTitle,
            @RequestPart("metaDescription") String metaDescription,
            @RequestPart("canonicalUrl") String canonicalUrl,
            @RequestPart("metaKeywords") String metaKeywords,
            @RequestPart(value = "socialMediaImage", required = false) MultipartFile socialMediaImage) {

        SEOResponseDTO createdSEO = seoService.createSEO(faviconImg, metaTitle, metaDescription,
                canonicalUrl, metaKeywords, socialMediaImage);
        return ResponseEntity.ok(createdSEO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SEOResponseDTO> getSEOById(@PathVariable Long id) {
        SEOResponseDTO seo = seoService.getSEOById(id);
        if (seo != null) {
            return ResponseEntity.ok(seo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<SEOResponseDTO>> getAllSEOs() {
        List<SEOResponseDTO> seos = seoService.getAllSEOs();
        return ResponseEntity.ok(seos);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SEOResponseDTO> updateSEO(
            @PathVariable Long id,
            @RequestPart(value = "faviconImg", required = false) MultipartFile faviconImg,
            @RequestPart(value = "metaTitle", required = false) String metaTitle,
            @RequestPart(value = "metaDescription", required = false) String metaDescription,
            @RequestPart(value = "canonicalUrl", required = false) String canonicalUrl,
            @RequestPart(value = "metaKeywords", required = false) String metaKeywords,
            @RequestPart(value = "socialMediaImage", required = false) MultipartFile socialMediaImage) {

        SEOResponseDTO updatedSEO = seoService.updateSEO(id, faviconImg, metaTitle, metaDescription,
                canonicalUrl, metaKeywords, socialMediaImage);
        if (updatedSEO != null) {
            return ResponseEntity.ok(updatedSEO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSEO(@PathVariable Long id) {
        seoService.deleteSEO(id);
        return ResponseEntity.ok().build();
    }
}
