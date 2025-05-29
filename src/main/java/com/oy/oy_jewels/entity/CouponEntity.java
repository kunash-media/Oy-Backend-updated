package com.oy.oy_jewels.entity;


import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "coupon_banner", columnDefinition = "LONGBLOB")
    private byte[] couponBanner; // Store image as byte array

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "coupon_code", unique = true, nullable = false)
    private String couponCode;

    @Column(name = "valid_date", nullable = false)
    private LocalDate validDate;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "discount_type", nullable = false)
    private String discountType; // PERCENTAGE or FIXED_AMOUNT

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    @Column(name = "description")
    private String description;

    // Constructors
    public CouponEntity() {}

    public CouponEntity(byte[] couponBanner, String couponName, String couponCode,
                        LocalDate validDate, LocalDate validUntil, String discountType,
                        Double discountValue, String description) {
        this.couponBanner = couponBanner;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.validDate = validDate;
        this.validUntil = validUntil;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.description = description;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getCouponBanner() {
        return couponBanner;
    }

    public void setCouponBanner(byte[] couponBanner) {
        this.couponBanner = couponBanner;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public LocalDate getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
