package com.oy.oy_jewels.dto.request;


public class AdminRequestDTO {
    private String name;
    private String mobileNumber;
    private String email;
    private String password;
    private String role;

    // Constructors
    public AdminRequestDTO() {}

    public AdminRequestDTO(String name, String mobileNumber, String email, String password, String role) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getName() { return name; }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}