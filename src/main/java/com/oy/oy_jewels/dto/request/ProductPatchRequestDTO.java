package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;
import java.util.List;

public class ProductPatchRequestDTO {

    private String productTitle;
    private BigDecimal productPrice;
    private BigDecimal productOldPrice;
    private byte[] productImage;
    private boolean productImagePresent = false;
    private String existingProductImage;  // New field for existing sub-image URLs
    private List<byte[]> productSubImages;
    private boolean productSubImagesPresent = false;
    private List<String> existingProductSubImages; // New field for existing sub-image URLs
    private String productDescription;
    private List<String> productFeatures;
    private List<String> productSizes;
    private List<String> productUnavailableSizes;
    private String productCategory;
    private String productStock;
    private Integer productQuantity;
    private String shopBy;
    private String productDiscount;
    private String productCouponCode;
    private String stoneColor;
    private String metalColor;
    private String skuNo;
    private String rating;



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

    public boolean isProductImagePresent() {
        return productImagePresent;
    }

    public void setProductImagePresent(boolean productImagePresent) {
        this.productImagePresent = productImagePresent;
    }

    public boolean isProductSubImagesPresent() {
        return productSubImagesPresent;
    }

    public void setProductSubImagesPresent(boolean productSubImagesPresent) {
        this.productSubImagesPresent = productSubImagesPresent;
    }

    public String getStoneColor() {
        return stoneColor;
    }

    public void setStoneColor(String stoneColor) {
        this.stoneColor = stoneColor;
    }

    public String getMetalColor() {
        return metalColor;
    }

    public void setMetalColor(String metalColor) {
        this.metalColor = metalColor;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getExistingProductImage() {
        return existingProductImage;
    }

    public void setExistingProductImage(String existingProductImage) {
        this.existingProductImage = existingProductImage;
    }

    public List<String> getExistingProductSubImages() {
        return existingProductSubImages;
    }

    public void setExistingProductSubImages(List<String> existingProductSubImages) {
        this.existingProductSubImages = existingProductSubImages;
    }
}