package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductCreateRequestDTO {

    private String productTitle;
    private BigDecimal productPrice;
    private BigDecimal productOldPrice;
    private byte[] productImage;
    private List<byte[]> productSubImages = new ArrayList<>();
    private String productDescription;
    private List<String> productFeatures = new ArrayList<>();
    private List<String> productSizes = new ArrayList<>();
    private List<String> productUnavailableSizes = new ArrayList<>();
    private String productCategory;
    private String productStock;
    private Integer productQuantity;
    private String shopBy;
    private String productDiscount;
    private String productCouponCode;

    // Constructors, Getters and Setters


    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(BigDecimal productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public List<byte[]> getProductSubImages() {
        return productSubImages;
    }

    public void setProductSubImages(List<byte[]> productSubImages) {
        this.productSubImages = productSubImages;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public List<String> getProductFeatures() {
        return productFeatures;
    }

    public void setProductFeatures(List<String> productFeatures) {
        this.productFeatures = productFeatures;
    }

    public List<String> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<String> productSizes) {
        this.productSizes = productSizes;
    }

    public List<String> getProductUnavailableSizes() {
        return productUnavailableSizes;
    }

    public void setProductUnavailableSizes(List<String> productUnavailableSizes) {
        this.productUnavailableSizes = productUnavailableSizes;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getShopBy() {
        return shopBy;
    }

    public void setShopBy(String shopBy) {
        this.shopBy = shopBy;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductCouponCode() {
        return productCouponCode;
    }

    public void setProductCouponCode(String productCouponCode) {
        this.productCouponCode = productCouponCode;
    }
}