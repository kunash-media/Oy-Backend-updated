package com.oy.oy_jewels.dto.response;

public class ShiprocketLoginResponse {
    private String token;
    private String message;
    private boolean success;

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}