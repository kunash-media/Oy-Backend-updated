package com.oy.oy_jewels.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class CustomerRequestDTO {


    private String customerName;


    private String email;


    private String mobileNumber;


    private String maritalStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate anniversary;


    private String status;

    // Default constructor
    public CustomerRequestDTO() {}

    // Parameterized constructor
    public CustomerRequestDTO(String customerName, String email, String mobileNumber,
                              String maritalStatus, LocalDate birthDate, LocalDate anniversary, String status) {
        this.customerName = customerName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.maritalStatus = maritalStatus;
        this.birthDate = birthDate;
        this.anniversary = anniversary;
        this.status = status;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(LocalDate anniversary) {
        this.anniversary = anniversary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
