package com.oy.oy_jewels.dto.request;

public class CancellationRequestDTO {


    private String cancellationTitle;


    private String cancellationDescription;

    // Default constructor
    public CancellationRequestDTO() {}

    // Parameterized constructor
    public CancellationRequestDTO(String cancellationTitle, String cancellationDescription) {
        this.cancellationTitle = cancellationTitle;
        this.cancellationDescription = cancellationDescription;
    }

    // Getters and Setters
    public String getCancellationTitle() {
        return cancellationTitle;
    }

    public void setCancellationTitle(String cancellationTitle) {
        this.cancellationTitle = cancellationTitle;
    }

    public String getCancellationDescription() {
        return cancellationDescription;
    }

    public void setCancellationDescription(String cancellationDescription) {
        this.cancellationDescription = cancellationDescription;
    }

    @Override
    public String toString() {
        return "CancellationRequestDTO{" +
                "cancellationTitle='" + cancellationTitle + '\'' +
                ", cancellationDescription='" + cancellationDescription + '\'' +
                '}';
    }
}
