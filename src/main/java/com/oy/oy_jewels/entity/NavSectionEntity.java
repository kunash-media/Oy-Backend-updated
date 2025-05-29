package com.oy.oy_jewels.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "nav_sections")
public class NavSectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "has_sub_options")
    private Boolean hasSubOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "navbar_id")
    @JsonIgnore
    private NavEntity navbar;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NavSubOptionEntity> subOptions;

    // Constructors
    public NavSectionEntity() {}

    public NavSectionEntity(String name, String path, Boolean hasSubOptions) {
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

    public NavEntity getNavbar() {
        return navbar;
    }

    public void setNavbar(NavEntity navbar) {
        this.navbar = navbar;
    }

    public List<NavSubOptionEntity> getSubOptions() {
        return subOptions;
    }

    public void setSubOptions(List<NavSubOptionEntity> subOptions) {
        this.subOptions = subOptions;
    }
}