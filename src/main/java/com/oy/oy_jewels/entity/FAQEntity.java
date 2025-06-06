package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "faq")
public class FAQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faq1_title")
    private String faq1Title;

    @Column(name = "faq1_description", columnDefinition = "TEXT")
    private String faq1Description;

    @Column(name = "faq2_title")
    private String faq2Title;

    @Column(name = "faq2_description", columnDefinition = "TEXT")
    private String faq2Description;

    @Column(name = "faq3_title")
    private String faq3Title;

    @Column(name = "faq3_description", columnDefinition = "TEXT")
    private String faq3Description;

    @Column(name = "faq4_title")
    private String faq4Title;

    @Column(name = "faq4_description", columnDefinition = "TEXT")
    private String faq4Description;

    @Column(name = "faq5_title")
    private String faq5Title;

    @Column(name = "faq5_description", columnDefinition = "TEXT")
    private String faq5Description;

    @Column(name = "faq6_title")
    private String faq6Title;

    @Column(name = "faq6_description", columnDefinition = "TEXT")
    private String faq6Description;

    // Default constructor
    public FAQEntity() {}

    // Constructor with all parameters
    public FAQEntity(String faq1Title, String faq1Description, String faq2Title, String faq2Description,
                     String faq3Title, String faq3Description, String faq4Title, String faq4Description,
                     String faq5Title, String faq5Description, String faq6Title, String faq6Description) {
        this.faq1Title = faq1Title;
        this.faq1Description = faq1Description;
        this.faq2Title = faq2Title;
        this.faq2Description = faq2Description;
        this.faq3Title = faq3Title;
        this.faq3Description = faq3Description;
        this.faq4Title = faq4Title;
        this.faq4Description = faq4Description;
        this.faq5Title = faq5Title;
        this.faq5Description = faq5Description;
        this.faq6Title = faq6Title;
        this.faq6Description = faq6Description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFaq1Title() { return faq1Title; }
    public void setFaq1Title(String faq1Title) { this.faq1Title = faq1Title; }

    public String getFaq1Description() { return faq1Description; }
    public void setFaq1Description(String faq1Description) { this.faq1Description = faq1Description; }

    public String getFaq2Title() { return faq2Title; }
    public void setFaq2Title(String faq2Title) { this.faq2Title = faq2Title; }

    public String getFaq2Description() { return faq2Description; }
    public void setFaq2Description(String faq2Description) { this.faq2Description = faq2Description; }

    public String getFaq3Title() { return faq3Title; }
    public void setFaq3Title(String faq3Title) { this.faq3Title = faq3Title; }

    public String getFaq3Description() { return faq3Description; }
    public void setFaq3Description(String faq3Description) { this.faq3Description = faq3Description; }

    public String getFaq4Title() { return faq4Title; }
    public void setFaq4Title(String faq4Title) { this.faq4Title = faq4Title; }

    public String getFaq4Description() { return faq4Description; }
    public void setFaq4Description(String faq4Description) { this.faq4Description = faq4Description; }

    public String getFaq5Title() { return faq5Title; }
    public void setFaq5Title(String faq5Title) { this.faq5Title = faq5Title; }

    public String getFaq5Description() { return faq5Description; }
    public void setFaq5Description(String faq5Description) { this.faq5Description = faq5Description; }

    public String getFaq6Title() { return faq6Title; }
    public void setFaq6Title(String faq6Title) { this.faq6Title = faq6Title; }

    public String getFaq6Description() { return faq6Description; }
    public void setFaq6Description(String faq6Description) { this.faq6Description = faq6Description; }
}


