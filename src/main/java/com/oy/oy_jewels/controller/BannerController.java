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
            @RequestPart(value = "bannerFileOne", required = false) MultipartFile bannerFileOne,
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


    // Get banner by page name
    @GetMapping("/get-by-pageName/{pageName}")
    public ResponseEntity<BannerEntity> getBannerByPageName(@PathVariable String pageName) {
        BannerEntity banner = bannerService.getBannerByPageName(pageName);
        return banner != null
                ? ResponseEntity.ok(banner)
                : ResponseEntity.notFound().build();
    }


    @PatchMapping(value = "/edit-banner/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerEntity> updateBanner(
            @PathVariable Long id,
            @RequestPart(value = "bannerData", required = false) BannerUpdateRequest bannerUpdateRequest,
            @RequestPart(value = "bannerFileOne", required = false) MultipartFile bannerFileOne,
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

    // Update banner
    @PutMapping("/{id}")
    public ResponseEntity<BannerEntity> updateBanner(
            @PathVariable Long id,
            @RequestParam("pageName") String pageName,
            @RequestParam("header") String header,
            @RequestParam("text") String text,
            @RequestParam(value = "bannerFileOne", required = false) MultipartFile bannerFileOne,
            @RequestParam(value = "bannerFileTwo", required = false) MultipartFile bannerFileTwo,
            @RequestParam(value = "bannerFileThree", required = false) MultipartFile bannerFileThree,
            @RequestParam(value = "bannerFileFour", required = false) MultipartFile bannerFileFour) {
        try {
            BannerEntity updatedBanner = bannerService.updateBannerWithImages(id, pageName, header, text,
                    bannerFileOne, bannerFileTwo, bannerFileThree, bannerFileFour);
            return new ResponseEntity<>(updatedBanner, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete banner
    @DeleteMapping("/delete-banner-page/{id}")
    public ResponseEntity<String> deleteBanner(@PathVariable Long id) {
        try {
            bannerService.deleteBanner(id);
            return new ResponseEntity<>("Banner Deleted Successfully!",HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get banner image by image number (1-4)
    @GetMapping("/{id}/image/{imageNumber}")
    public ResponseEntity<byte[]> getBannerImage(@PathVariable Long id, @PathVariable int imageNumber) {
        try {
            byte[] image = bannerService.getBannerImage(id, imageNumber);
            if (image != null && image.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search banners by header
    @GetMapping("/search")
    public ResponseEntity<List<BannerEntity>> searchBannersByHeader(@RequestParam String header) {
        try {
            List<BannerEntity> banners = bannerService.searchBannersByHeader(header);
            return new ResponseEntity<>(banners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get banners with images
    @GetMapping("/with-images")
    public ResponseEntity<List<BannerEntity>> getBannersWithImages() {
        try {
            List<BannerEntity> banners = bannerService.getBannersWithImages();
            return new ResponseEntity<>(banners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count images for a banner
    @GetMapping("/{id}/image-count")
    public ResponseEntity<Integer> countImagesForBanner(@PathVariable Long id) {
        try {
            Integer count = bannerService.countImagesForBanner(id);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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