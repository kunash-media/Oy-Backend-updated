package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.SEOEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SEOService {

    SEOEntity createSEO(MultipartFile faviconImg, String metaTitle, String metaDescription,
                        String canonicalUrl, String metaKeywords, MultipartFile socialMediaImage);

    SEOEntity getSEOById(Long id);

    List<SEOEntity> getAllSEOs();

    SEOEntity updateSEO(Long id, MultipartFile faviconImg, String metaTitle, String metaDescription,
                        String canonicalUrl, String metaKeywords, MultipartFile socialMediaImage);

    void deleteSEO(Long id);
}
