package com.oy.oy_jewels.dto.request;


public class TermsRequestDTO {


    private String termsTitle;

    private String termsDescription;

    // Default constructor
    public TermsRequestDTO() {}

    // Constructor with parameters
    public TermsRequestDTO(String termsTitle, String termsDescription) {
        this.termsTitle = termsTitle;
        this.termsDescription = termsDescription;
    }

    // Getters and Setters
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
        return "TermsRequestDTO{" +
                "termsTitle='" + termsTitle + '\'' +
                ", termsDescription='" + termsDescription + '\'' +
                '}';
    }
}

