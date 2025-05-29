package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "terms_policies")
public class TermsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "terms_title")
    private String termsTitle;

    @Column(name = "terms_description", length = 1000)
    private String termsDescription;

    // Default constructor
    public TermsEntity() {}

    // Constructor with parameters
    public TermsEntity(String termsTitle, String termsDescription) {
        this.termsTitle = termsTitle;
        this.termsDescription = termsDescription;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermsTitle() {
        return termsTitle;
    }

    public void setTermsTitle(String termsTitle) {
        this.termsTitle = termsTitle;
    }

    public String getTermsDescription() {
        return termsDescription;
    }

    public void setTermsDescription(String termsDescription) {
        this.termsDescription = termsDescription;
    }

    @Override
    public String toString() {
        return "TermsEntity{" +
                "id=" + id +
                ", termsTitle='" + termsTitle + '\'' +
                ", termsDescription='" + termsDescription + '\'' +
                '}';
    }
}