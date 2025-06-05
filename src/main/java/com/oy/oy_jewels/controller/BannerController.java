package com.oy.oy_jewels.controller;


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

@RestController
@RequestMapping("/api/banners")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    // Create banner with images
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerEntity> createBanner(
            @RequestParam("pageName") String pageName,
            @RequestParam("header") String header,
            @RequestParam("text") String text,
            @RequestParam(value = "bannerFileOne", required = false) MultipartFile bannerFileOne,
            @RequestParam(value = "bannerFileTwo", required = false) MultipartFile bannerFileTwo,
            @RequestParam(value = "bannerFileThree", required = false) MultipartFile bannerFileThree,
            @RequestParam(value = "bannerFileFour", required = false) MultipartFile bannerFileFour) {
        try {
            BannerEntity banner = bannerService.saveBannerWithImages(pageName, header, text,
                    bannerFileOne, bannerFileTwo, bannerFileThree, bannerFileFour);
            return new ResponseEntity<>(banner, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all banners
    @GetMapping
    public ResponseEntity<List<BannerEntity>> getAllBanners() {
        try {
            List<BannerEntity> banners = bannerService.getAllBanners();
            return new ResponseEntity<>(banners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping("/page/{pageName}")
    public ResponseEntity<BannerEntity> getBannerByPageName(@PathVariable String pageName) {
        try {
            Optional<BannerEntity> banner = bannerService.getBannerByPageName(pageName);
            return banner.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all banners by page name
    @GetMapping("/pages/{pageName}")
    public ResponseEntity<List<BannerEntity>> getBannersByPageName(@PathVariable String pageName) {
        try {
            List<BannerEntity> banners = bannerService.getBannersByPageName(pageName);
            return new ResponseEntity<>(banners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBanner(@PathVariable Long id) {
        try {
            bannerService.deleteBanner(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    // Check if banner exists for page
    @GetMapping("/exists/{pageName}")
    public ResponseEntity<Boolean> existsByPageName(@PathVariable String pageName) {
        try {
            boolean exists = bannerService.existsByPageName(pageName);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}