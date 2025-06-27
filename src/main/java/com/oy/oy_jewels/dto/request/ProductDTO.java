package com.oy.oy_jewels.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("ProductTitle")
    private String productTitle;

    @JsonProperty("ProductPrice")
    private BigDecimal productPrice;

    @JsonProperty("ProductOldPrice")
    private BigDecimal productOldPrice;

    @JsonProperty("ProductImage")
    private byte[] productImage;

    @JsonProperty("ProductSubImages")
    private List<byte[]> productSubImages = new ArrayList<>();

    @JsonProperty("ProductDescription")
    private String productDescription;

    @JsonProperty("ProductFeatures")
    private List<String> productFeatures = new ArrayList<>();

    @JsonProperty("ProductSizes")
    private List<String> productSizes = new ArrayList<>();

    @JsonProperty("ProductUnavailableSizes")
    private List<String> productUnavailableSizes = new ArrayList<>();

    @JsonProperty("ProductCategory")
    private String productCategory;

    @JsonProperty("ProductStock")
    private String productStock;

    @JsonProperty("productQuantity")
    private Integer productQuantity;

    @JsonProperty("shopBy")
    private String shopBy;

    @JsonProperty("productDiscount")
    private String productDiscount;

    @JsonProperty("productCouponCode")
    private String productCouponCode;

    @JsonProperty("stoneColor")
    private String stoneColor;

    @JsonProperty("metalColor")
    private String metalColor;

    @JsonProperty("skuNo")
    private String skuNo;

    @JsonProperty("rating")
    private String rating;

    // Constructors, Getters and Setters

    public ProductDTO(Long productId, String productTitle, BigDecimal productPrice, BigDecimal productOldPrice,
                      byte[] productImage, List<byte[]> productSubImages, String productDescription,
                      List<String> productFeatures, List<String> productSizes, List<String> productUnavailableSizes, String productCategory, String productStock,
                      Integer productQuantity, String shopBy, String productDiscount, String productCouponCode,
                      String stoneColor, String metalColor, String skuNo, String rating) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productOldPrice = productOldPrice;
        this.productImage = productImage;
        this.productSubImages = productSubImages;
        this.productDescription = productDescription;
        this.productFeatures = productFeatures;
        this.productSizes = productSizes;
        this.productUnavailableSizes = productUnavailableSizes;
        this.productCategory = productCategory;
        this.productStock = productStock;
        this.productQuantity = productQuantity;
        this.shopBy = shopBy;
        this.productDiscount = productDiscount;
        this.productCouponCode = productCouponCode;
        this.stoneColor = stoneColor;
        this.metalColor = metalColor;
        this.skuNo = skuNo;
        this.rating = rating;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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
}
