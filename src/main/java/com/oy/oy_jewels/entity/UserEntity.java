package com.oy.oy_jewels.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "customer_name")
    private String customerName;

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

    // Constructors
    public UserEntity() {}

    public UserEntity(Long userId, String customerName, String email,
                      String mobile, String maritalStatus, LocalDate customerDOB,
                      LocalDate anniversary, String status, String password) {
        this.userId = userId;
        this.customerName = customerName;
        this.email = email;
        this.mobile = mobile;
        this.maritalStatus = maritalStatus;
        this.customerDOB = customerDOB;
        this.anniversary = anniversary;
        this.status = status;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
