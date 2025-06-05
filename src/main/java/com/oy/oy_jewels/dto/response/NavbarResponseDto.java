package com.oy.oy_jewels.dto.response;

// NavbarResponseDto.java


import com.oy.oy_jewels.dto.request.NavSectionDto;

import java.util.List;

public class NavbarResponseDto {
    private Long id;
    private String headerText;
    private boolean hasLogo;
    private List<NavSectionDto> sections;

    // Constructors
    public NavbarResponseDto() {}

    public NavbarResponseDto(Long id, String headerText, boolean hasLogo, List<NavSectionDto> sections) {
        this.id = id;
        this.headerText = headerText;
        this.hasLogo = hasLogo;
        this.sections = sections;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public boolean isHasLogo() {
        return hasLogo;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public List<NavSectionDto> getSections() {
        return sections;
    }

    public void setSections(List<NavSectionDto> sections) {
        this.sections = sections;
    }
}

