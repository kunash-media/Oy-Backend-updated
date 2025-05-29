package com.oy.oy_jewels.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "nav_sub_options")
public class NavSubOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    @JsonIgnore
    private NavSectionEntity section;

    // Constructors
    public NavSubOptionEntity() {}

    public NavSubOptionEntity(String name, String path) {
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

    public NavSectionEntity getSection() {
        return section;
    }

    public void setSection(NavSectionEntity section) {
        this.section = section;
    }
}
