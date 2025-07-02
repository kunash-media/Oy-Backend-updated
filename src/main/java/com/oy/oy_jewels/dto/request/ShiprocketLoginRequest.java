package com.oy.oy_jewels.dto.request;

public class ShiprocketLoginRequest {
    private String email;
    private String password;

    public ShiprocketLoginRequest() {}

    public ShiprocketLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
