package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "disclaimers")
public class DisclaimerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disclaimer_title")
    private String disclaimerTitle;

    @Column(name = "disclaimer_description", length = 2000)
    private String disclaimerDescription;

    @Column(name = "section_number")
    private Integer sectionNumber;

    // Default constructor
    public DisclaimerEntity() {}

    // Constructor with parameters
    public DisclaimerEntity(String disclaimerTitle, String disclaimerDescription, Integer sectionNumber) {
        this.disclaimerTitle = disclaimerTitle;
        this.disclaimerDescription = disclaimerDescription;
        this.sectionNumber = sectionNumber;
    }

    // Constructor without section number (for descriptions without titles)
    public DisclaimerEntity(String disclaimerDescription) {
        this.disclaimerDescription = disclaimerDescription;
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
        return "DisclaimerEntity{" +
                "id=" + id +
                ", disclaimerTitle='" + disclaimerTitle + '\'' +
                ", disclaimerDescription='" + disclaimerDescription + '\'' +
                ", sectionNumber=" + sectionNumber +
                '}';
    }
}
