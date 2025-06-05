package com.oy.oy_jewels.dto.response;

// ApiResponse.java (Generic response wrapper)


public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // Default constructor
    public ApiResponse() {}

    // Constructor for success response
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Static factory methods
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}




// ApiResponse.java
//package com.oy.oy_jewels.dto.response;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import java.time.LocalDateTime;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class ApiResponse<T> {
//
//    private boolean success;
//    private String message;
//    private T data;
//    private LocalDateTime timestamp;
//    private String error;
//
//    // Default constructor
//    public ApiResponse() {
//        this.timestamp = LocalDateTime.now();
//    }
//
//    // Constructor for success response with data
//    public ApiResponse(boolean success, String message, T data) {
//        this();
//        this.success = success;
//        this.message = message;
//        this.data = data;
//    }
//
//    // Constructor for success response without data
//    public ApiResponse(boolean success, String message) {
//        this();
//        this.success = success;
//        this.message = message;
//    }
//
//    // Constructor for error response
//    public ApiResponse(boolean success, String message, String error) {
//        this();
//        this.success = success;
//        this.message = message;
//        this.error = error;
//    }
//
//    // Static methods for common responses
//    public static <T> ApiResponse<T> success(String message, T data) {
//        return new ApiResponse<>(true, message, data);
//    }
//
//    public static <T> ApiResponse<T> success(String message) {
//        return new ApiResponse<>(true, message);
//    }
//
//    public static <T> ApiResponse<T> error(String message, String error) {
//        return new ApiResponse<>(false, message, error);
//    }
//
//    public static <T> ApiResponse<T> error(String message) {
//        return new ApiResponse<>(false, message);
//    }
//
//    // Getters and Setters
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(LocalDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public String getError() {
//        return error;
//    }
//
//    public void setError(String error) {
//        this.error = error;
//    }
//}