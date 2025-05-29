package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class OffersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "page_header_background", columnDefinition = "LONGBLOB")
    private byte[] pageHeaderBackground;

    @Column(name = "page_title")
    private String pageTitle;

    @Column(name = "super_discount_active_coupons")
    private String superDiscountActiveCoupons;

    // Default constructor
    public OffersEntity() {}

    // Constructor with parameters
    public OffersEntity(byte[] pageHeaderBackground, String pageTitle, String superDiscountActiveCoupons) {
        this.pageHeaderBackground = pageHeaderBackground;
        this.pageTitle = pageTitle;
        this.superDiscountActiveCoupons = superDiscountActiveCoupons;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPageHeaderBackground() {
        return pageHeaderBackground;
    }

    public void setPageHeaderBackground(byte[] pageHeaderBackground) {
        this.pageHeaderBackground = pageHeaderBackground;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getSuperDiscountActiveCoupons() {
        return superDiscountActiveCoupons;
    }

    public void setSuperDiscountActiveCoupons(String superDiscountActiveCoupons) {
        this.superDiscountActiveCoupons = superDiscountActiveCoupons;
    }
}
