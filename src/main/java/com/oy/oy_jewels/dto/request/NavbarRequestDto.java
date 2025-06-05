package com.oy.oy_jewels.dto.request;

import java.util.List;

public class NavbarRequestDto {
    private String headerText;
    private List<NavSectionDto> sections;

    // Constructors
    public NavbarRequestDto() {}

    public NavbarRequestDto(String headerText, List<NavSectionDto> sections) {
        this.headerText = headerText;
        this.sections = sections;
    }

    // Getters and Setters
    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public List<NavSectionDto> getSections() {
        return sections;
    }

    public void setSections(List<NavSectionDto> sections) {
        this.sections = sections;
    }
}
