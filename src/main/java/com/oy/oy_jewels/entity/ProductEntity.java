package com.oy.oy_jewels.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE product_id = ?")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_old_price")
    private BigDecimal productOldPrice;

    @Column(name = "product_image", columnDefinition = "LONGBLOB")
    private byte[] productImage;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_sub_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "sub_image", columnDefinition = "LONGBLOB")
    private List<byte[]> productSubImages = new ArrayList<>();

    @Column(name = "product_description", columnDefinition = "LONGTEXT")
    private String productDescription;

    @ElementCollection
    @CollectionTable(name = "product_features", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "feature")
    private List<String> productFeatures = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<String> productSizes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_unavailable_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "unavailable_size")
    private List<String> productUnavailableSizes = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @Column(name = "category")
    private String productCategory;

    @Column(name = "stock")
    private String productStock;

    @Column(name = "quantity")
    private Integer productQuantity;

    @Column(name = "shop_by")
    private String shopBy;

    @Column(name = "discount")
    private String productDiscount;

    @Column(name = "coupon_code")
    private String productCouponCode;

    @Column(name = "stone_color")
    private String stoneColor;

    @Column(name = "metal_color")
    private String metalColor;

    @Column(name = "sku_no")
    private String skuNo;

    @Column(name = "rating")
    private String rating;

    public static Specification<ProductEntity> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
    }

    // Constructors
    public ProductEntity() {}

    public ProductEntity(Long productId, String productTitle, BigDecimal productPrice,
                         BigDecimal productOldPrice, byte[] productImage, List<byte[]> productSubImages,
                         String productDescription, List<String> productFeatures, List<String> productSizes,
                         List<String> productUnavailableSizes, String productCategory, boolean isDeleted, String productStock,
                         Integer productQuantity, String shopBy, String productDiscount,
                         String productCouponCode, String stoneColor, String metalColor, String skuNo,
                         String rating) {
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
        this.isDeleted =  isDeleted;
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

    // Getters and Setters (keeping existing structure intact)
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public BigDecimal getProductOldPrice() { return productOldPrice; }
    public void setProductOldPrice(BigDecimal productOldPrice) { this.productOldPrice = productOldPrice; }

    public byte[] getProductImage() { return productImage; }
    public void setProductImage(byte[] productImage) { this.productImage = productImage; }

    public List<byte[]> getProductSubImages() { return productSubImages; }
    public void setProductSubImages(List<byte[]> productSubImages) { this.productSubImages = productSubImages; }

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
