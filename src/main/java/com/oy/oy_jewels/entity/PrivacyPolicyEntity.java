package com.oy.oy_jewels.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "privacy_policy")
public class PrivacyPolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_description", columnDefinition = "TEXT")
    private String policyDescription;

    @Column(name = "policy_title1")
    private String policyTitle1;

    @Column(name = "policy_description1", columnDefinition = "TEXT")
    private String policyDescription1;

    @Column(name = "policy_title2")
    private String policyTitle2;

    @Column(name = "policy_description2", columnDefinition = "TEXT")
    private String policyDescription2;

    @Column(name = "policy_title3")
    private String policyTitle3;

    @Column(name = "policy_description3", columnDefinition = "TEXT")
    private String policyDescription3;

    @Column(name = "policy_title4")
    private String policyTitle4;

    @Column(name = "policy_description4", columnDefinition = "TEXT")
    private String policyDescription4;

    @Column(name = "policy_title5")
    private String policyTitle5;

    @Column(name = "policy_description5", columnDefinition = "TEXT")
    private String policyDescription5;

    @Column(name = "policy_title6")
    private String policyTitle6;

    @Column(name = "policy_description6", columnDefinition = "TEXT")
    private String policyDescription6;

    @Column(name = "policy_title7")
    private String policyTitle7;

    @Column(name = "policy_description7", columnDefinition = "TEXT")
    private String policyDescription7;

    @Column(name = "policy_title8")
    private String policyTitle8;

    @Column(name = "policy_description8", columnDefinition = "TEXT")
    private String policyDescription8;

    @Column(name = "policy_title9")
    private String policyTitle9;

    @Column(name = "policy_description9", columnDefinition = "TEXT")
    private String policyDescription9;

    @Column(name = "policy_title10")
    private String policyTitle10;

    @Column(name = "policy_description10", columnDefinition = "TEXT")
    private String policyDescription10;

    @Column(name = "page_title11")
    private String pageTitle11;

    // Default constructor
    public PrivacyPolicyEntity() {}

    // Parameterized constructor
    public PrivacyPolicyEntity(String policyDescription, String policyTitle1, String policyDescription1,
                               String policyTitle2, String policyDescription2, String policyTitle3,
                               String policyDescription3, String policyTitle4, String policyDescription4,
                               String policyTitle5, String policyDescription5, String policyTitle6,
                               String policyDescription6, String policyTitle7, String policyDescription7,
                               String policyTitle8, String policyDescription8, String policyTitle9,
                               String policyDescription9, String policyTitle10, String policyDescription10,
                               String pageTitle11) {
        this.policyDescription = policyDescription;
        this.policyTitle1 = policyTitle1;
        this.policyDescription1 = policyDescription1;
        this.policyTitle2 = policyTitle2;
        this.policyDescription2 = policyDescription2;
        this.policyTitle3 = policyTitle3;
        this.policyDescription3 = policyDescription3;
        this.policyTitle4 = policyTitle4;
        this.policyDescription4 = policyDescription4;
        this.policyTitle5 = policyTitle5;
        this.policyDescription5 = policyDescription5;
        this.policyTitle6 = policyTitle6;
        this.policyDescription6 = policyDescription6;
        this.policyTitle7 = policyTitle7;
        this.policyDescription7 = policyDescription7;
        this.policyTitle8 = policyTitle8;
        this.policyDescription8 = policyDescription8;
        this.policyTitle9 = policyTitle9;
        this.policyDescription9 = policyDescription9;
        this.policyTitle10 = policyTitle10;
        this.policyDescription10 = policyDescription10;
        this.pageTitle11 = pageTitle11;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyDescription() {
        return policyDescription;
    }

    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    public String getPolicyTitle1() {
        return policyTitle1;
    }

    public void setPolicyTitle1(String policyTitle1) {
        this.policyTitle1 = policyTitle1;
    }

    public String getPolicyDescription1() {
        return policyDescription1;
    }

    public void setPolicyDescription1(String policyDescription1) {
        this.policyDescription1 = policyDescription1;
    }

    public String getPolicyTitle2() {
        return policyTitle2;
    }

    public void setPolicyTitle2(String policyTitle2) {
        this.policyTitle2 = policyTitle2;
    }

    public String getPolicyDescription2() {
        return policyDescription2;
    }

    public void setPolicyDescription2(String policyDescription2) {
        this.policyDescription2 = policyDescription2;
    }

    public String getPolicyTitle3() {
        return policyTitle3;
    }

    public void setPolicyTitle3(String policyTitle3) {
        this.policyTitle3 = policyTitle3;
    }

    public String getPolicyDescription3() {
        return policyDescription3;
    }

    public void setPolicyDescription3(String policyDescription3) {
        this.policyDescription3 = policyDescription3;
    }

    public String getPolicyTitle4() {
        return policyTitle4;
    }

    public void setPolicyTitle4(String policyTitle4) {
        this.policyTitle4 = policyTitle4;
    }

    public String getPolicyDescription4() {
        return policyDescription4;
    }

    public void setPolicyDescription4(String policyDescription4) {
        this.policyDescription4 = policyDescription4;
    }

    public String getPolicyTitle5() {
        return policyTitle5;
    }

    public void setPolicyTitle5(String policyTitle5) {
        this.policyTitle5 = policyTitle5;
    }

    public String getPolicyDescription5() {
        return policyDescription5;
    }

    public void setPolicyDescription5(String policyDescription5) {
        this.policyDescription5 = policyDescription5;
    }

    public String getPolicyTitle6() {
        return policyTitle6;
    }

    public void setPolicyTitle6(String policyTitle6) {
        this.policyTitle6 = policyTitle6;
    }

    public String getPolicyDescription6() {
        return policyDescription6;
    }

    public void setPolicyDescription6(String policyDescription6) {
        this.policyDescription6 = policyDescription6;
    }

    public String getPolicyTitle7() {
        return policyTitle7;
    }

    public void setPolicyTitle7(String policyTitle7) {
        this.policyTitle7 = policyTitle7;
    }

    public String getPolicyDescription7() {
        return policyDescription7;
    }

    public void setPolicyDescription7(String policyDescription7) {
        this.policyDescription7 = policyDescription7;
    }

    public String getPolicyTitle8() {
        return policyTitle8;
    }

    public void setPolicyTitle8(String policyTitle8) {
        this.policyTitle8 = policyTitle8;
    }

    public String getPolicyDescription8() {
        return policyDescription8;
    }

    public void setPolicyDescription8(String policyDescription8) {
        this.policyDescription8 = policyDescription8;
    }

    public String getPolicyTitle9() {
        return policyTitle9;
    }

    public void setPolicyTitle9(String policyTitle9) {
        this.policyTitle9 = policyTitle9;
    }

    public String getPolicyDescription9() {
        return policyDescription9;
    }

    public void setPolicyDescription9(String policyDescription9) {
        this.policyDescription9 = policyDescription9;
    }

    public String getPolicyTitle10() {
        return policyTitle10;
    }

    public void setPolicyTitle10(String policyTitle10) {
        this.policyTitle10 = policyTitle10;
    }

    public String getPolicyDescription10() {
        return policyDescription10;
    }

    public void setPolicyDescription10(String policyDescription10) {
        this.policyDescription10 = policyDescription10;
    }

    public String getPageTitle11() {
        return pageTitle11;
    }

    public void setPageTitle11(String pageTitle11) {
        this.pageTitle11 = pageTitle11;
    }
}
