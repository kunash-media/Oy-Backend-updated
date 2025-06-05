package com.oy.oy_jewels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oy.oy_jewels.dto.request.OffersRequestDto;
import com.oy.oy_jewels.dto.response.OffersResponseDto;
import com.oy.oy_jewels.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OffersResponseDto> createOffers(
            @RequestPart(value = "pageHeaderBackground", required = false) MultipartFile pageHeaderBackground,
            @RequestPart("offersData") String offersDataJson) {

        try {
            OffersRequestDto requestDto = objectMapper.readValue(offersDataJson, OffersRequestDto.class);
            OffersResponseDto createdOffers = offersService.createOffers(pageHeaderBackground, requestDto);
            return ResponseEntity.ok(createdOffers);
        } catch (Exception e) {
            throw new RuntimeException("Error processing offers data", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffersResponseDto> getOffersById(@PathVariable Long id) {
        OffersResponseDto offers = offersService.getOffersById(id);
        if (offers != null) {
            return ResponseEntity.ok(offers);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<OffersResponseDto>> getAllOffers() {
        List<OffersResponseDto> offers = offersService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OffersResponseDto> updateOffers(
            @PathVariable Long id,
            @RequestPart(value = "pageHeaderBackground", required = false) MultipartFile pageHeaderBackground,
            @RequestPart(value = "offersData", required = false) String offersDataJson) {

        try {
            OffersRequestDto requestDto = null;
            if (offersDataJson != null) {
                requestDto = objectMapper.readValue(offersDataJson, OffersRequestDto.class);
            }
            OffersResponseDto updatedOffers = offersService.updateOffers(id, pageHeaderBackground, requestDto);
            if (updatedOffers != null) {
                return ResponseEntity.ok(updatedOffers);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException("Error updating offers data", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOffers(@PathVariable Long id) {
        offersService.deleteOffers(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/page-header-background")
    public ResponseEntity<byte[]> getPageHeaderBackground(@PathVariable Long id) {
        byte[] image = offersService.getPageHeaderBackground(id);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(image);
    }
}
