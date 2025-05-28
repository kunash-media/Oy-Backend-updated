package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "category")
    private String category;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "offer")
    private String offer;

    @Column(name = "gst")
    private BigDecimal gst;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "stock")
    private String stock; // instock/outofstock

    @Column(name = "shop_by")
    private String shopBy;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    // One product can be in many orders
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    // Constructors
    public ProductEntity() {}

    public ProductEntity(String productName, String category, String couponCode, Integer quantity,
                         String offer, BigDecimal gst, BigDecimal discount, BigDecimal productPrice,
                         String stock, String shopBy, BigDecimal totalPrice) {
        this.productName = productName;
        this.category = category;
        this.couponCode = couponCode;
        this.quantity = quantity;
        this.offer = offer;
        this.gst = gst;
        this.discount = discount;
        this.productPrice = productPrice;
        this.stock = stock;
        this.shopBy = shopBy;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getOffer() { return offer; }
    public void setOffer(String offer) { this.offer = offer; }

    public BigDecimal getGst() { return gst; }
    public void setGst(BigDecimal gst) { this.gst = gst; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public String getStock() { return stock; }
    public void setStock(String stock) { this.stock = stock; }

    public String getShopBy() { return shopBy; }
    public void setShopBy(String shopBy) { this.shopBy = shopBy; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public List<OrderEntity> getOrders() { return orders; }
    public void setOrders(List<OrderEntity> orders) { this.orders = orders; }


}

