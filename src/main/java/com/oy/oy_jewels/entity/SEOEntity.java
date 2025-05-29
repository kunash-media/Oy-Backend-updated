package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seo")
public class SEOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "favicon_img", columnDefinition = "LONGBLOB")
    private byte[] faviconImg;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "canonical_url")
    private String canonicalUrl;

    @Column(name = "meta_keywords")
    private String metaKeywords;

    @Lob
    @Column(name = "social_media_image", columnDefinition = "LONGBLOB")
    private byte[] socialMediaImage;

    // Default constructor
    public SEOEntity() {}

    // Constructor with parameters
    public SEOEntity(byte[] faviconImg, String metaTitle, String metaDescription,
                     String canonicalUrl, String metaKeywords, byte[] socialMediaImage) {
        this.faviconImg = faviconImg;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.canonicalUrl = canonicalUrl;
        this.metaKeywords = metaKeywords;
        this.socialMediaImage = socialMediaImage;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFaviconImg() {
        return faviconImg;
    }

    public void setFaviconImg(byte[] faviconImg) {
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

    public byte[] getSocialMediaImage() {
        return socialMediaImage;
    }

    public void setSocialMediaImage(byte[] socialMediaImage) {
        this.socialMediaImage = socialMediaImage;
    }
}