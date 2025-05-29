package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.OffersEntity;
import com.oy.oy_jewels.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @PostMapping("/create")
    public ResponseEntity<OffersEntity> createOffers(
            @RequestPart(value = "pageHeaderBackground", required = false) MultipartFile pageHeaderBackground,
            @RequestPart("pageTitle") String pageTitle,
            @RequestPart("superDiscountActiveCoupons") String superDiscountActiveCoupons) {

        OffersEntity createdOffers = offersService.createOffers(pageHeaderBackground, pageTitle, superDiscountActiveCoupons);
        return ResponseEntity.ok(createdOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffersEntity> getOffersById(@PathVariable Long id) {
        OffersEntity offers = offersService.getOffersById(id);
        if (offers != null) {
            return ResponseEntity.ok(offers);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<OffersEntity>> getAllOffers() {
        List<OffersEntity> offers = offersService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OffersEntity> updateOffers(
            @PathVariable Long id,
            @RequestPart(value = "pageHeaderBackground", required = false) MultipartFile pageHeaderBackground,
            @RequestPart(value = "pageTitle", required = false) String pageTitle,
            @RequestPart(value = "superDiscountActiveCoupons", required = false) String superDiscountActiveCoupons) {

        OffersEntity updatedOffers = offersService.updateOffers(id, pageHeaderBackground, pageTitle, superDiscountActiveCoupons);
        if (updatedOffers != null) {
            return ResponseEntity.ok(updatedOffers);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOffers(@PathVariable Long id) {
        offersService.deleteOffers(id);
        return ResponseEntity.ok().build();
    }
}
