package com.oy.oy_jewels.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cancellation_policies")
public class CancellationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cancellation_title", length = 100)
    private String cancellationTitle;

    @Column(name = "cancellation_description", length = 1000)
    private String cancellationDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public CancellationEntity() {}

    // Parameterized constructor
    public CancellationEntity(String cancellationTitle, String cancellationDescription) {
        this.cancellationTitle = cancellationTitle;
        this.cancellationDescription = cancellationDescription;
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
        return "CancellationEntity{" +
                "id=" + id +
                ", cancellationTitle='" + cancellationTitle + '\'' +
                ", cancellationDescription='" + cancellationDescription + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CancellationEntity)) return false;
        CancellationEntity that = (CancellationEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}