package com.oy.oy_jewels.service;


import com.oy.oy_jewels.dto.response.SEOResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SEOService {

    SEOResponseDTO createSEO(MultipartFile faviconImg, String metaTitle, String metaDescription,
                             String canonicalUrl, String metaKeywords, MultipartFile socialMediaImage);

    SEOResponseDTO getSEOById(Long id);

    List<SEOResponseDTO> getAllSEOs();

    SEOResponseDTO updateSEO(Long id, MultipartFile faviconImg, String metaTitle, String metaDescription,
                             String canonicalUrl, String metaKeywords, MultipartFile socialMediaImage);

    void deleteSEO(Long id);
}

