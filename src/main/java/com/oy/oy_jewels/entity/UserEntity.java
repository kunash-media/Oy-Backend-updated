package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "customer_dob")
    private LocalDate customerDOB;

    @Column(name = "anniversary")
    private LocalDate anniversary;

    @Column(name = "status")
    private String status; // active/inactive

    // One user can have many orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    // Constructors
    public UserEntity() {}

    public UserEntity(String customerName, String email, String mobile, String maritalStatus,
                      LocalDate customerDOB, LocalDate anniversary, String status) {
        this.customerName = customerName;
        this.email = email;
        this.mobile = mobile;
        this.maritalStatus = maritalStatus;
        this.customerDOB = customerDOB;
        this.anniversary = anniversary;
        this.status = status;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

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

    public List<OrderEntity> getOrders() { return orders; }
    public void setOrders(List<OrderEntity> orders) { this.orders = orders; }


}


