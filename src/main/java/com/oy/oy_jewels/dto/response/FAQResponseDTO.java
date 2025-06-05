package com.oy.oy_jewels.dto.response;



public class FAQResponseDTO {
    private Long id;
    private String faq1Title;
    private String faq1Description;
    private String faq2Title;
    private String faq2Description;
    private String faq3Title;
    private String faq3Description;
    private String faq4Title;
    private String faq4Description;
    private String faq5Title;
    private String faq5Description;
    private String faq6Title;
    private String faq6Description;

    // Default constructor
    public FAQResponseDTO() {}

    // Constructor with all parameters
    public FAQResponseDTO(Long id, String faq1Title, String faq1Description, String faq2Title, String faq2Description,
                          String faq3Title, String faq3Description, String faq4Title, String faq4Description,
                          String faq5Title, String faq5Description, String faq6Title, String faq6Description) {
        this.id = id;
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
