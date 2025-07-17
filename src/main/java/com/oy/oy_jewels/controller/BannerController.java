package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.BannerRequest;
import com.oy.oy_jewels.dto.request.BannerUpdateRequest;
import com.oy.oy_jewels.dto.response.BannerResponse;
import com.oy.oy_jewels.entity.BannerEntity;
import com.oy.oy_jewels.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    // Create banner with images
    @PostMapping(value = "/create-banner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerEntity> createBanner(
            @RequestPart(value = "bannerData", required = false) BannerRequest bannerRequest,
            @RequestPart(value = "bannerFileOne", required = false) MultipartFile[] bannerFileOne,
            @RequestPart(value = "bannerFileTwo", required = false) MultipartFile bannerFileTwo,
            @RequestPart(value = "bannerFileThree", required = false) MultipartFile bannerFileThree,
            @RequestPart(value = "bannerFileFour", required = false) MultipartFile bannerFileFour) {

        try {
            BannerEntity banner = bannerService.saveBannerWithImages(
                    bannerRequest.getPageName(),
                    bannerRequest.getHeader(),
                    bannerRequest.getText(),
                    bannerFileOne, bannerFileTwo, bannerFileThree, bannerFileFour
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(banner);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get banner by ID
    @GetMapping("/{id}")
    public ResponseEntity<BannerEntity> getBannerById(@PathVariable Long id) {
        try {
            Optional<BannerEntity> banner = bannerService.getBannerById(id);
            return banner.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // patch api
    @PatchMapping(value = "/edit-banner/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerEntity> updateBanner(
            @PathVariable Long id,
            @RequestPart(value = "bannerData", required = false) BannerUpdateRequest bannerUpdateRequest,
            @RequestPart(value = "bannerFileOne", required = false) MultipartFile[] bannerFileOne,
            @RequestPart(value = "bannerFileTwo", required = false) MultipartFile bannerFileTwo,
            @RequestPart(value = "bannerFileThree", required = false) MultipartFile bannerFileThree,
            @RequestPart(value = "bannerFileFour", required = false) MultipartFile bannerFileFour) {

        try {
            BannerEntity updatedBanner = bannerService.updateBanner(
                    id,
                    bannerUpdateRequest,
                    bannerFileOne, bannerFileTwo, bannerFileThree, bannerFileFour
            );
            return ResponseEntity.ok(updatedBanner);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //get all banner.
    @GetMapping("/get-all-banners")
    public ResponseEntity<List<BannerResponse>> getAllBanners() {
        try {
            List<BannerEntity> banners = bannerService.getAllBanners();

            if (banners.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<BannerResponse> response = banners.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete banner by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        try {
            bannerService.deleteBanner(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get banner by page name
    @GetMapping("/get-by-pageName/{pageName}")
    public ResponseEntity<BannerEntity> getBannerByPageName(@PathVariable String pageName) {
        try {
            BannerEntity banner = bannerService.getBannerByPageName(pageName);
            if (banner != null) {
                return ResponseEntity.ok(banner);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Search banners by header
    @GetMapping("/search")
    public ResponseEntity<List<BannerEntity>> searchBannersByHeader(@RequestParam String header) {
        try {
            List<BannerEntity> banners = bannerService.searchBannersByHeader(header);
            if (banners.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(banners);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get banners with images
    @GetMapping("/with-images")
    public ResponseEntity<List<BannerEntity>> getBannersWithImages() {
        try {
            List<BannerEntity> banners = bannerService.getBannersWithImages();
            if (banners.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(banners);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private BannerResponse convertToResponse(BannerEntity banner) {
        return new BannerResponse(
                banner.getId(),
                banner.getPageName(),
                banner.getHeader(),
                banner.getText(),
                banner.getBannerFileOne(),
                banner.getBannerFileTwo(),
                banner.getBannerFileThree(),
                banner.getBannerFileFour()
        );
    }
}