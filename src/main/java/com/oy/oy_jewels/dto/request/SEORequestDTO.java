package com.oy.oy_jewels.dto.request;

// SEORequestDTO.java


public class SEORequestDTO {
    private String metaTitle;
    private String metaDescription;
    private String canonicalUrl;
    private String metaKeywords;

    // Default constructor
    public SEORequestDTO() {}

    // Constructor with parameters
    public SEORequestDTO(String metaTitle, String metaDescription, String canonicalUrl, String metaKeywords) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.canonicalUrl = canonicalUrl;
        this.metaKeywords = metaKeywords;
    }

    // Getters and Setters
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
}
