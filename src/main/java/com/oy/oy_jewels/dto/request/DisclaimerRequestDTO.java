package com.oy.oy_jewels.dto.request;


public class DisclaimerRequestDTO {


    private String disclaimerTitle;


    private String disclaimerDescription;

    private Integer sectionNumber;

    // Default constructor
    public DisclaimerRequestDTO() {}

    // Constructor with all parameters
    public DisclaimerRequestDTO(String disclaimerTitle, String disclaimerDescription, Integer sectionNumber) {
        this.disclaimerTitle = disclaimerTitle;
        this.disclaimerDescription = disclaimerDescription;
        this.sectionNumber = sectionNumber;
    }

    // Constructor without section number
    public DisclaimerRequestDTO(String disclaimerDescription) {
        this.disclaimerDescription = disclaimerDescription;
    }

    // Getters and Setters
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
        return "DisclaimerRequestDTO{" +
                "disclaimerTitle='" + disclaimerTitle + '\'' +
                ", disclaimerDescription='" + disclaimerDescription + '\'' +
                ", sectionNumber=" + sectionNumber +
                '}';
    }
}
