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

    // Billing address fields
    @Column(name = "billing_customer_name")
    private String billingCustomerName;

    @Column(name = "billing_last_name")
    private String billingLastName;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "billing_address_2")
    private String billingAddress2;

    @Column(name = "billing_city")
    private String billingCity;

    @Column(name = "billing_pincode")
    private String billingPincode;

    @Column(name = "billing_state")
    private String billingState;

    @Column(name = "billing_country")
    private String billingCountry = "India";

    @Column(name = "billing_email")
    private String billingEmail;

    @Column(name = "billing_phone")
    private String billingPhone;

    @Column(name = "shipping_is_billing")
    private Boolean shippingIsBilling = true;

    @Column(name = "shipping_last_name")
    private String shippingLastName;

    @Column(name = "shipping_address_2")
    private String shippingAddress2;

    @Column(name = "shipping_email")
    private String shippingEmail;

    @Column(name = "shipping_phone")
    private String shippingPhone;

    @Column(name = "pickup_location")
    private String pickupLocation = "Primary";

    @Column(name = "channel_id")
    private String channelId = "1";

    // Package dimensions
    @Column(name = "package_length")
    private Integer packageLength;

    @Column(name = "package_breadth")
    private Integer packageBreadth;

    @Column(name = "package_height")
    private Integer packageHeight;

    @Column(name = "package_weight")
    private Integer packageWeight;

    // One order can have many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    // Constructors
    public OrderEntity() {}

    // Getters and Setters
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

    public String getBillingCustomerName() { return billingCustomerName; }
    public void setBillingCustomerName(String billingCustomerName) { this.billingCustomerName = billingCustomerName; }

    public String getBillingLastName() { return billingLastName; }
    public void setBillingLastName(String billingLastName) { this.billingLastName = billingLastName; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getBillingAddress2() { return billingAddress2; }
    public void setBillingAddress2(String billingAddress2) { this.billingAddress2 = billingAddress2; }

    public String getBillingCity() { return billingCity; }
    public void setBillingCity(String billingCity) { this.billingCity = billingCity; }

    public String getBillingPincode() { return billingPincode; }
    public void setBillingPincode(String billingPincode) { this.billingPincode = billingPincode; }

    public String getBillingState() { return billingState; }
    public void setBillingState(String billingState) { this.billingState = billingState; }

    public String getBillingCountry() { return billingCountry; }
    public void setBillingCountry(String billingCountry) { this.billingCountry = billingCountry; }

    public String getBillingEmail() { return billingEmail; }
    public void setBillingEmail(String billingEmail) { this.billingEmail = billingEmail; }

    public String getBillingPhone() { return billingPhone; }
    public void setBillingPhone(String billingPhone) { this.billingPhone = billingPhone; }

    public Boolean getShippingIsBilling() { return shippingIsBilling; }
    public void setShippingIsBilling(Boolean shippingIsBilling) { this.shippingIsBilling = shippingIsBilling; }

    public String getShippingLastName() { return shippingLastName; }
    public void setShippingLastName(String shippingLastName) { this.shippingLastName = shippingLastName; }

    public String getShippingAddress2() { return shippingAddress2; }
    public void setShippingAddress2(String shippingAddress2) { this.shippingAddress2 = shippingAddress2; }

    public String getShippingEmail() { return shippingEmail; }
    public void setShippingEmail(String shippingEmail) { this.shippingEmail = shippingEmail; }

    public String getShippingPhone() { return shippingPhone; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getChannelId() { return channelId; }
    public void setChannelId(String channelId) { this.channelId = channelId; }

    public Integer getPackageLength() { return packageLength; }
    public void setPackageLength(Integer packageLength) { this.packageLength = packageLength; }

    public Integer getPackageBreadth() { return packageBreadth; }
    public void setPackageBreadth(Integer packageBreadth) { this.packageBreadth = packageBreadth; }

    public Integer getPackageHeight() { return packageHeight; }
    public void setPackageHeight(Integer packageHeight) { this.packageHeight = packageHeight; }

    public Integer getPackageWeight() { return packageWeight; }
    public void setPackageWeight(Integer packageWeight) { this.packageWeight = packageWeight; }

    public List<OrderItemEntity> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemEntity> orderItems) { this.orderItems = orderItems; }
}