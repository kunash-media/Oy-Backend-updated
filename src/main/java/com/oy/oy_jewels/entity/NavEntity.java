package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "navbar")
public class NavEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "logo", columnDefinition = "LONGBLOB")
    private byte[] logo;

    @Column(name = "header_text")
    private String headerText;

    @OneToMany(mappedBy = "navbar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NavSectionEntity> sections;

    // Constructors
    public NavEntity() {}

    public NavEntity(byte[] logo, String headerText) {
        this.logo = logo;
        this.headerText = headerText;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public List<NavSectionEntity> getSections() {
        return sections;
    }

    public void setSections(List<NavSectionEntity> sections) {
        this.sections = sections;
    }
}