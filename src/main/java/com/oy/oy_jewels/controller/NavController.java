package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.NavEntity;
import com.oy.oy_jewels.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/navbar")
public class NavController {

    @Autowired
    private NavService navService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NavEntity> createNavbar(
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart("headerText") String headerText,
            @RequestPart("sections") String sectionsJson) {

        NavEntity navbar = navService.createNavbar(logo, headerText, sectionsJson);
        return ResponseEntity.ok(navbar);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NavEntity> updateNavbar(
            @PathVariable Long id,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "headerText", required = false) String headerText,
            @RequestPart(value = "sections", required = false) String sectionsJson) {

        NavEntity navbar = navService.updateNavbar(id, logo, headerText, sectionsJson);
        return ResponseEntity.ok(navbar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NavEntity> getNavbarById(@PathVariable Long id) {
        NavEntity navbar = navService.getNavbarById(id);
        return ResponseEntity.ok(navbar);
    }

    @GetMapping("/get-All-Navbars")
    public ResponseEntity<List<NavEntity>> getAllNavbars() {
        List<NavEntity> navbars = navService.getAllNavbars();
        return ResponseEntity.ok(navbars);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNavbar(@PathVariable Long id) {
        navService.deleteNavbar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/logo")
    public ResponseEntity<byte[]> getNavbarLogo(@PathVariable Long id) {
        byte[] logo = navService.getNavbarLogo(id);

        if (logo == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(logo.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(logo);
    }
}