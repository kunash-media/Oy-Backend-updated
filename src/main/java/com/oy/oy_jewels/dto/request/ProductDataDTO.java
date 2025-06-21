package com.oy.oy_jewels.dto.request;

import java.util.List;

public class ProductDataDTO {

    private String productTitle;
    private String productPrice;
    private String productOldPrice;
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

    // Getters and setters
    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }

    public String getProductPrice() { return productPrice; }
    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }

    public String getProductOldPrice() { return productOldPrice; }
    public void setProductOldPrice(String productOldPrice) { this.productOldPrice = productOldPrice; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public List<String> getProductFeatures() { return productFeatures; }
    public void setProductFeatures(List<String> productFeatures) { this.productFeatures = productFeatures; }

    public List<String> getProductSizes() { return productSizes; }
    public void setProductSizes(List<String> productSizes) { this.productSizes = productSizes; }

    public List<String> getProductUnavailableSizes() { return productUnavailableSizes; }
    public void setProductUnavailableSizes(List<String> productUnavailableSizes) { this.productUnavailableSizes = productUnavailableSizes; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public String getProductStock() { return productStock; }
    public void setProductStock(String productStock) { this.productStock = productStock; }

    public Integer getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }

    public String getShopBy() { return shopBy; }
    public void setShopBy(String shopBy) { this.shopBy = shopBy; }

    public String getProductDiscount() { return productDiscount; }
    public void setProductDiscount(String productDiscount) { this.productDiscount = productDiscount; }

    public String getProductCouponCode() { return productCouponCode; }
    public void setProductCouponCode(String productCouponCode) { this.productCouponCode = productCouponCode; }
}