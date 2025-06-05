package com.oy.oy_jewels.dto.response;

import java.time.LocalDateTime;

public class CancellationResponseDTO {

    private Long id;
    private String cancellationTitle;
    private String cancellationDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public CancellationResponseDTO() {}

    // Parameterized constructor
    public CancellationResponseDTO(Long id, String cancellationTitle, String cancellationDescription,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cancellationTitle = cancellationTitle;
        this.cancellationDescription = cancellationDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CancellationResponseDTO{" +
                "id=" + id +
                ", cancellationTitle='" + cancellationTitle + '\'' +
                ", cancellationDescription='" + cancellationDescription + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}