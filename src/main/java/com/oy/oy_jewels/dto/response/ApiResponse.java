package com.oy.oy_jewels.dto.response;


public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String error;

    // Default constructor
    public ApiResponse() {}

    // Constructor for success response
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public <T> ApiResponse(boolean b, String success, T data, Object o) {
    }


    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data,null);
    }

    public static <T> ApiResponse<T> error(String message,String error) {
        return new ApiResponse<>(false, message, null);
    }

    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, null, error);
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

public String getError() {
    return error;
}

    public  void setError(String error) {
    this.error = error;
}


    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error='" + error + '\'' + '}';
    }
}



