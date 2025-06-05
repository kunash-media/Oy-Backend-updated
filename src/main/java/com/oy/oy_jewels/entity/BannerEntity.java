package com.oy.oy_jewels.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "banners")
public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_name")
    private String pageName;

    @Column(name = "header")
    private String header;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "banner_file_one", columnDefinition = "LONGBLOB")
    private byte[] bannerFileOne;

    @Column(name = "banner_file_two", columnDefinition = "LONGBLOB")
    private byte[] bannerFileTwo;

    @Column(name = "banner_file_three", columnDefinition = "LONGBLOB")
    private byte[] bannerFileThree;

    @Column(name = "banner_file_four", columnDefinition = "LONGBLOB")
    private byte[] bannerFileFour;

    // Default constructor
    public BannerEntity() {}

    // Constructor with parameters
    public BannerEntity(String pageName, String header, String text) {
        this.pageName = pageName;
        this.header = header;
        this.text = text;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getBannerFileOne() {
        return bannerFileOne;
    }

    public void setBannerFileOne(byte[] bannerFileOne) {
        this.bannerFileOne = bannerFileOne;
    }

    public byte[] getBannerFileTwo() {
        return bannerFileTwo;
    }

    public void setBannerFileTwo(byte[] bannerFileTwo) {
        this.bannerFileTwo = bannerFileTwo;
    }

    public byte[] getBannerFileThree() {
        return bannerFileThree;
    }

    public void setBannerFileThree(byte[] bannerFileThree) {
        this.bannerFileThree = bannerFileThree;
    }

    public byte[] getBannerFileFour() {
        return bannerFileFour;
    }

    public void setBannerFileFour(byte[] bannerFileFour) {
        this.bannerFileFour = bannerFileFour;
    }

    @Override
    public String toString() {
        return "BannerEntity{" +
                "id=" + id +
                ", pageName='" + pageName + '\'' +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                ", hasImage1=" + (bannerFileOne != null) +
                ", hasImage2=" + (bannerFileTwo != null) +
                ", hasImage3=" + (bannerFileThree != null) +
                ", hasImage4=" + (bannerFileFour != null) +
                '}';
    }
}