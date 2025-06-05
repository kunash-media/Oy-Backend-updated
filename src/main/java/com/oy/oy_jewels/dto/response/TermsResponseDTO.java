package com.oy.oy_jewels.dto.response;


public class TermsResponseDTO {

    private Long id;
    private String termsTitle;
    private String termsDescription;

    // Default constructor
    public TermsResponseDTO() {}

    // Constructor with parameters
    public TermsResponseDTO(Long id, String termsTitle, String termsDescription) {
        this.id = id;
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
        return "TermsResponseDTO{" +
                "id=" + id +
                ", termsTitle='" + termsTitle + '\'' +
                ", termsDescription='" + termsDescription + '\'' +
                '}';
    }
}

