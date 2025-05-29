package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.SEOEntity;
import com.oy.oy_jewels.repository.SEORepository;
import com.oy.oy_jewels.service.SEOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SEOServiceImpl implements SEOService {

    @Autowired
    private SEORepository seoRepository;

    @Override
    public SEOEntity createSEO(MultipartFile faviconImg, String metaTitle, String metaDescription,
                               String canonicalUrl, String metaKeywords, MultipartFile socialMediaImage) {
        SEOEntity seoEntity = new SEOEntity();

        try {
            if (faviconImg != null && !faviconImg.isEmpty()) {
                seoEntity.setFaviconImg(faviconImg.getBytes());
            }

            seoEntity.setMetaTitle(metaTitle);
            seoEntity.setMetaDescription(metaDescription);
            seoEntity.setCanonicalUrl(canonicalUrl);
            seoEntity.setMetaKeywords(metaKeywords);

            if (socialMediaImage != null && !socialMediaImage.isEmpty()) {
                seoEntity.setSocialMediaImage(socialMediaImage.getBytes());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing image files", e);
        }

        return seoRepository.save(seoEntity);
    }

    @Override
    public SEOEntity getSEOById(Long id) {
        return seoRepository.findById(id).orElse(null);
    }

    @Override
    public List<SEOEntity> getAllSEOs() {
        return seoRepository.findAll();
    }

    @Override
    public SEOEntity updateSEO(Long id, MultipartFile faviconImg, String metaTitle, String metaDescription,
                               String canonicalUrl, String metaKeywords, MultipartFile socialMediaImage) {
        SEOEntity existingSEO = seoRepository.findById(id).orElse(null);

        if (existingSEO == null) {
            return null;
        }

        try {
            if (faviconImg != null && !faviconImg.isEmpty()) {
                existingSEO.setFaviconImg(faviconImg.getBytes());
            }

            if (metaTitle != null) {
                existingSEO.setMetaTitle(metaTitle);
            }

            if (metaDescription != null) {
                existingSEO.setMetaDescription(metaDescription);
            }

            if (canonicalUrl != null) {
                existingSEO.setCanonicalUrl(canonicalUrl);
            }

            if (metaKeywords != null) {
                existingSEO.setMetaKeywords(metaKeywords);
            }

            if (socialMediaImage != null && !socialMediaImage.isEmpty()) {
                existingSEO.setSocialMediaImage(socialMediaImage.getBytes());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing image files", e);
        }

        return seoRepository.save(existingSEO);
    }

    @Override
    public void deleteSEO(Long id) {
        seoRepository.deleteById(id);
    }
}