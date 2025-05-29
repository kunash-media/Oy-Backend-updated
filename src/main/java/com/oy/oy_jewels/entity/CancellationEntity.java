package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cancellation_policies")
public class CancellationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cancellation_title")
    private String cancellationTitle;

    @Column(name = "cancellation_description", length = 1000)
    private String cancellationDescription;

    // Default constructor
    public CancellationEntity() {}

    // Constructor with parameters
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

    @Override
    public String toString() {
        return "CancellationEntity{" +
                "id=" + id +
                ", cancellationTitle='" + cancellationTitle + '\'' +
                ", cancellationDescription='" + cancellationDescription + '\'' +
                '}';
    }
}
