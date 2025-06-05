package com.oy.oy_jewels.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oy.oy_jewels.dto.request.NavbarRequestDto;
import com.oy.oy_jewels.dto.response.NavbarResponseDto;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NavbarResponseDto> createNavbar(
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart("navbarData") String navbarDataJson) {

        try {
            NavbarRequestDto requestDto = objectMapper.readValue(navbarDataJson, NavbarRequestDto.class);
            NavbarResponseDto navbar = navService.createNavbar(logo, requestDto);
            return ResponseEntity.ok(navbar);
        } catch (Exception e) {
            throw new RuntimeException("Error processing navbar data", e);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NavbarResponseDto> updateNavbar(
            @PathVariable Long id,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "navbarData", required = false) String navbarDataJson) {

        try {
            NavbarRequestDto requestDto = null;
            if (navbarDataJson != null) {
                requestDto = objectMapper.readValue(navbarDataJson, NavbarRequestDto.class);
            }
            NavbarResponseDto navbar = navService.updateNavbar(id, logo, requestDto);
            return ResponseEntity.ok(navbar);
        } catch (Exception e) {
            throw new RuntimeException("Error updating navbar data", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NavbarResponseDto> getNavbarById(@PathVariable Long id) {
        NavbarResponseDto navbar = navService.getNavbarById(id);
        return ResponseEntity.ok(navbar);
    }

    @GetMapping("/get-All-Navbars")
    public ResponseEntity<List<NavbarResponseDto>> getAllNavbars() {
        List<NavbarResponseDto> navbars = navService.getAllNavbars();
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
