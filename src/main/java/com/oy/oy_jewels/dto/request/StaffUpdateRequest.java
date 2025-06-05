package com.oy.oy_jewels.dto.request;

public class StaffUpdateRequest {


    private String staffName;

    private String emailAddress;

    private String password;

    private String contactNumber;


    private String joiningDate;


    private String staffRole;

    // Default constructor
    public StaffUpdateRequest() {}

    // Parameterized constructor
    public StaffUpdateRequest(String staffName, String emailAddress, String password,
                              String contactNumber, String joiningDate, String staffRole) {
        this.staffName = staffName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.contactNumber = contactNumber;
        this.joiningDate = joiningDate;
        this.staffRole = staffRole;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    @Override
    public String toString() {
        return "StaffUpdateRequest{" +
                "staffName='" + staffName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", joiningDate='" + joiningDate + '\'' +
                ", staffRole='" + staffRole + '\'' +
                '}';
    }
}

