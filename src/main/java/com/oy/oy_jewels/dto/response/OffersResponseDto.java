package com.oy.oy_jewels.dto.response;

// OffersResponseDto.java


public class OffersResponseDto {
    private Long id;
    private String pageTitle;
    private String superDiscountActiveCoupons;
    private boolean hasPageHeaderBackground;

    // Constructors
    public OffersResponseDto() {}

    public OffersResponseDto(Long id, String pageTitle, String superDiscountActiveCoupons, boolean hasPageHeaderBackground) {
        this.id = id;
        this.pageTitle = pageTitle;
        this.superDiscountActiveCoupons = superDiscountActiveCoupons;
        this.hasPageHeaderBackground = hasPageHeaderBackground;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isHasPageHeaderBackground() {
        return hasPageHeaderBackground;
    }

    public void setHasPageHeaderBackground(boolean hasPageHeaderBackground) {
        this.hasPageHeaderBackground = hasPageHeaderBackground;
    }
}

