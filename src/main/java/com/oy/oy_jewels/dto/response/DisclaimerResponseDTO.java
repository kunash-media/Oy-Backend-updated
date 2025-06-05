package com.oy.oy_jewels.dto.response;


public class DisclaimerResponseDTO {

    private Long id;
    private String disclaimerTitle;
    private String disclaimerDescription;
    private Integer sectionNumber;

    // Default constructor
    public DisclaimerResponseDTO() {}

    // Constructor with all parameters
    public DisclaimerResponseDTO(Long id, String disclaimerTitle, String disclaimerDescription, Integer sectionNumber) {
        this.id = id;
        this.disclaimerTitle = disclaimerTitle;
        this.disclaimerDescription = disclaimerDescription;
        this.sectionNumber = sectionNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisclaimerTitle() {
        return disclaimerTitle;
    }

    public void setDisclaimerTitle(String disclaimerTitle) {
        this.disclaimerTitle = disclaimerTitle;
    }

    public String getDisclaimerDescription() {
        return disclaimerDescription;
    }

    public void setDisclaimerDescription(String disclaimerDescription) {
        this.disclaimerDescription = disclaimerDescription;
    }

    public Integer getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(Integer sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    @Override
    public String toString() {
        return "DisclaimerResponseDTO{" +
                "id=" + id +
                ", disclaimerTitle='" + disclaimerTitle + '\'' +
                ", disclaimerDescription='" + disclaimerDescription + '\'' +
                ", sectionNumber=" + sectionNumber +
                '}';
    }
}
