package com.oy.oy_jewels.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "our_staff")
public class OurStaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "staff_image", columnDefinition = "LONGBLOB")
    private byte[] staffImage;

    @Column(name = "staff_name", nullable = false)
    private String staffName;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "staff_role")
    private String staffRole;

    // Default constructor
    public OurStaffEntity() {}

    // Modified parameterized constructor
    public OurStaffEntity(byte[] staffImage, String staffName, String emailAddress,
                          String password, String contactNumber, LocalDate joiningDate, String staffRole) {
        this.staffImage = staffImage;
        this.staffName = staffName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.contactNumber = contactNumber;
        this.joiningDate = joiningDate;
        this.staffRole = staffRole;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getStaffImage() {
        return staffImage;
    }

    public void setStaffImage(byte[] staffImage) {
        this.staffImage = staffImage;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }
}
