package com.oy.oy_jewels.dto.request;

// OffersRequestDto.java


public class OffersRequestDto {
    private String pageTitle;
    private String superDiscountActiveCoupons;

    // Constructors
    public OffersRequestDto() {}

    public OffersRequestDto(String pageTitle, String superDiscountActiveCoupons) {
        this.pageTitle = pageTitle;
        this.superDiscountActiveCoupons = superDiscountActiveCoupons;
    }

    // Getters and Setters
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

