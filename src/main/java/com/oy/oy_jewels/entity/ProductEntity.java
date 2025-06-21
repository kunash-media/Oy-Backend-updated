package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
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

    // Constructors
    public ProductEntity() {}

    public ProductEntity(Long productId, String productTitle, BigDecimal productPrice,
                         BigDecimal productOldPrice, byte[] productImage, List<byte[]> productSubImages,
                         String productDescription, List<String> productFeatures, List<String> productSizes,
                         List<String> productUnavailableSizes, String productCategory, String productStock,
                         Integer productQuantity, String shopBy, String productDiscount, String productCouponCode) {
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
}
