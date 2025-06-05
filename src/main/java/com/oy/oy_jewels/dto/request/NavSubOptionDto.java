package com.oy.oy_jewels.dto.request;

// NavSubOptionDto.java


public class NavSubOptionDto {
    private Long id;
    private String name;
    private String path;

    // Constructors
    public NavSubOptionDto() {}

    public NavSubOptionDto(String name, String path) {
        this.name = name;
        this.path = path;
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
}

