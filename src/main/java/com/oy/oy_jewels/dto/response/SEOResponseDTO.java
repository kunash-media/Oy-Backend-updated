package com.oy.oy_jewels.dto.response;

// SEOResponseDTO.java

import java.util.Base64;

public class SEOResponseDTO {
    private Long id;
    private String faviconImg; // Base64 encoded string
    private String metaTitle;
    private String metaDescription;
    private String canonicalUrl;
    private String metaKeywords;
    private String socialMediaImage; // Base64 encoded string

    // Default constructor
    public SEOResponseDTO() {}

    // Constructor with parameters
    public SEOResponseDTO(Long id, byte[] faviconImg, String metaTitle, String metaDescription,
                          String canonicalUrl, String metaKeywords, byte[] socialMediaImage) {
        this.id = id;
        this.faviconImg = faviconImg != null ? Base64.getEncoder().encodeToString(faviconImg) : null;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.canonicalUrl = canonicalUrl;
        this.metaKeywords = metaKeywords;
        this.socialMediaImage = socialMediaImage != null ? Base64.getEncoder().encodeToString(socialMediaImage) : null;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaviconImg() {
        return faviconImg;
    }

    public void setFaviconImg(String faviconImg) {
        this.faviconImg = faviconImg;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getSocialMediaImage() {
        return socialMediaImage;
    }

    public void setSocialMediaImage(String socialMediaImage) {
        this.socialMediaImage = socialMediaImage;
    }
}

