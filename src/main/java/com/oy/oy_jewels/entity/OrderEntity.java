package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // User relationship - Many orders belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // Enhanced shipping address fields for Shiprocket
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_city")
    private String shippingCity;

    @Column(name = "shipping_state")
    private String shippingState;

    @Column(name = "shipping_pincode")
    private String shippingPincode;

    @Column(name = "shipping_country")
    private String shippingCountry = "India";

    // Customer details for shipping
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "order_status")
    private String orderStatus; // processing, completed, cancelled, etc.

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    // Shiprocket specific fields
    @Column(name = "shiprocket_order_id")
    private String shiprocketOrderId;

    @Column(name = "shiprocket_shipment_id")
    private String shiprocketShipmentId;

    @Column(name = "awb_code")
    private String awbCode; // Air Waybill number from courier

    @Column(name = "courier_company_id")
    private Integer courierCompanyId;

    @Column(name = "tracking_url")
    private String trackingUrl;

    // One order can have many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    // Constructors, getters, setters
    public OrderEntity() {}

    // Add all getters and setters here
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getShippingCity() { return shippingCity; }
    public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }

    public String getShippingState() { return shippingState; }
    public void setShippingState(String shippingState) { this.shippingState = shippingState; }

    public String getShippingPincode() { return shippingPincode; }
    public void setShippingPincode(String shippingPincode) { this.shippingPincode = shippingPincode; }

    public String getShippingCountry() { return shippingCountry; }
    public void setShippingCountry(String shippingCountry) { this.shippingCountry = shippingCountry; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getShiprocketOrderId() { return shiprocketOrderId; }
    public void setShiprocketOrderId(String shiprocketOrderId) { this.shiprocketOrderId = shiprocketOrderId; }

    public String getShiprocketShipmentId() { return shiprocketShipmentId; }
    public void setShiprocketShipmentId(String shiprocketShipmentId) { this.shiprocketShipmentId = shiprocketShipmentId; }

    public String getAwbCode() { return awbCode; }
    public void setAwbCode(String awbCode) { this.awbCode = awbCode; }

    public Integer getCourierCompanyId() { return courierCompanyId; }
    public void setCourierCompanyId(Integer courierCompanyId) { this.courierCompanyId = courierCompanyId; }

    public String getTrackingUrl() { return trackingUrl; }
    public void setTrackingUrl(String trackingUrl) { this.trackingUrl = trackingUrl; }

    public List<OrderItemEntity> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemEntity> orderItems) { this.orderItems = orderItems; }
}
