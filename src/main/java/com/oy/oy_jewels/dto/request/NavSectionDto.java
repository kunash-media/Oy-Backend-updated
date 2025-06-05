package com.oy.oy_jewels.dto.request;

// NavSectionDto.java


import java.util.List;

public class NavSectionDto {
    private Long id;
    private String name;
    private String path;
    private Boolean hasSubOptions;
    private List<NavSubOptionDto> subOptions;

    // Constructors
    public NavSectionDto() {}

    public NavSectionDto(String name, String path, Boolean hasSubOptions) {
        this.name = name;
        this.path = path;
        this.hasSubOptions = hasSubOptions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHasSubOptions() {
        return hasSubOptions;
    }

    public void setHasSubOptions(Boolean hasSubOptions) {
        this.hasSubOptions = hasSubOptions;
    }

    public List<NavSubOptionDto> getSubOptions() {
        return subOptions;
    }

    public void setSubOptions(List<NavSubOptionDto> subOptions) {
        this.subOptions = subOptions;
    }
}

