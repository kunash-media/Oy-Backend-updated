package com.oy.oy_jewels.dto.response;


import java.time.LocalDate;
import java.util.Base64;

public class StaffResponse {
    private Long id;
    private String staffImageBase64;
    private String staffName;
    private String emailAddress;
    private String contactNumber;
    private LocalDate joiningDate;
    private String staffRole;

    // Default constructor
    public StaffResponse() {}

    // Parameterized constructor
    public StaffResponse(Long id, byte[] staffImage, String staffName, String emailAddress,
                         String contactNumber, LocalDate joiningDate, String staffRole) {
        this.id = id;
        this.staffImageBase64 = staffImage != null ? Base64.getEncoder().encodeToString(staffImage) : null;
        this.staffName = staffName;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.joiningDate = joiningDate;
        this.staffRole = staffRole;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffImageBase64() {
        return staffImageBase64;
    }

    public void setStaffImageBase64(String staffImageBase64) {
        this.staffImageBase64 = staffImageBase64;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }
}
