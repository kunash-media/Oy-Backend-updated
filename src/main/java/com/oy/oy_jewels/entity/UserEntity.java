package com.oy.oy_jewels.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "marital_status")
    private String maritalStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "customer_dob")
    private LocalDate customerDOB;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "anniversary")
    private LocalDate anniversary;

    @Column(name = "status")
    private String status; // active/inactive

    @Column(name = "user_password")
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ShippingAddressEntity> shippingAddresses = new ArrayList<>();

    // Add relationship with PaymentOrder
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PaymentOrder> paymentOrders = new ArrayList<>();

    // Constructors
    public UserEntity() {}


    public UserEntity(Long userId, String customerFirstName, String customerLastName,
                      String email, String mobile, String maritalStatus, LocalDate customerDOB,
                      LocalDate anniversary, String status, String password,
                      List<ShippingAddressEntity> shippingAddresses, List<PaymentOrder> paymentOrders) {
        this.userId = userId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.email = email;
        this.mobile = mobile;
        this.maritalStatus = maritalStatus;
        this.customerDOB = customerDOB;
        this.anniversary = anniversary;
        this.status = status;
        this.password = password;
        this.shippingAddresses = shippingAddresses;
        this.paymentOrders = paymentOrders;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCustomerName() { return customerFirstName; }
    public void setCustomerName(String customerFirstName) { this.customerFirstName = customerFirstName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }

    public LocalDate getCustomerDOB() { return customerDOB; }
    public void setCustomerDOB(LocalDate customerDOB) { this.customerDOB = customerDOB; }

    public LocalDate getAnniversary() { return anniversary; }
    public void setAnniversary(LocalDate anniversary) { this.anniversary = anniversary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ShippingAddressEntity> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<ShippingAddressEntity> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public List<PaymentOrder> getPaymentOrders() {
        return paymentOrders;
    }

    public void setPaymentOrders(List<PaymentOrder> paymentOrders) {
        this.paymentOrders = paymentOrders;
    }
}
