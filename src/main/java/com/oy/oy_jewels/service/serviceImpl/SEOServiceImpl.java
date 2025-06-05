package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.response.SEOResponseDTO;
import com.oy.oy_jewels.entity.SEOEntity;
import com.oy.oy_jewels.repository.SEORepository;
import com.oy.oy_jewels.service.SEOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SEOServiceImpl implements SEOService {

    @Autowired
    private SEORepository seoRepository;

    @Override
    public SEOResponseDTO createSEO(MultipartFile faviconImg, String metaTitle, String metaDescription,
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

        SEOEntity savedEntity = seoRepository.save(seoEntity);
        return convertToResponseDTO(savedEntity);
    }

    @Override
    public SEOResponseDTO getSEOById(Long id) {
        SEOEntity seoEntity = seoRepository.findById(id).orElse(null);
        return seoEntity != null ? convertToResponseDTO(seoEntity) : null;
    }

    @Override
    public List<SEOResponseDTO> getAllSEOs() {
        List<SEOEntity> seoEntities = seoRepository.findAll();
        return seoEntities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SEOResponseDTO updateSEO(Long id, MultipartFile faviconImg, String metaTitle, String metaDescription,
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

        SEOEntity updatedEntity = seoRepository.save(existingSEO);
        return convertToResponseDTO(updatedEntity);
    }

    @Override
    public void deleteSEO(Long id) {
        seoRepository.deleteById(id);
    }

    private SEOResponseDTO convertToResponseDTO(SEOEntity seoEntity) {
        return new SEOResponseDTO(
                seoEntity.getId(),
                seoEntity.getFaviconImg(),
                seoEntity.getMetaTitle(),
                seoEntity.getMetaDescription(),
                seoEntity.getCanonicalUrl(),
                seoEntity.getMetaKeywords(),
                seoEntity.getSocialMediaImage()
        );
    }
}