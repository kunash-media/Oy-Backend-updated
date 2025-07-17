package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    // Many order items belong to one order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    // Many order items can reference one product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_price") // Price at the time of order
    private BigDecimal productPrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    // Add these fields to OrderItemEntity class

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "sku")
    private String sku;

    @Column(name = "units")
    private Integer units;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "discount")
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "tax")
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(name = "hsn")
    private String hsn;

    // Constructors
    public OrderItemEntity() {}

    public OrderItemEntity(Long orderItemId, OrderEntity order, ProductEntity product,
                           Integer quantity, BigDecimal productPrice,
                           BigDecimal subtotal, String itemName, String sku,
                           Integer units, BigDecimal sellingPrice, BigDecimal discount,
                           BigDecimal tax, String hsn) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.subtotal = subtotal;
        this.itemName = itemName;
        this.sku = sku;
        this.units = units;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.tax = tax;
        this.hsn = hsn;
    }


    // Getters and Setters
    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }

    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }
}