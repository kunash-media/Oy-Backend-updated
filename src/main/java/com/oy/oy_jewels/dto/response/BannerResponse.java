package com.oy.oy_jewels.dto.response;

import java.util.List;

public class BannerResponse {
    private Long id;
    private String pageName;
    private String header;
    private String text;
    private List<byte[]> bannerFileOne;
    private byte[] bannerFileTwo;
    private byte[] bannerFileThree;
    private byte[] bannerFileFour;

    // Default constructor
    public BannerResponse() {}

    // Constructor with parameters
    public BannerResponse(Long id, String pageName, String header, String text,
                          List<byte[]> bannerFileOne, byte[] bannerFileTwo,
                          byte[] bannerFileThree, byte[] bannerFileFour) {
        this.id = id;
        this.pageName = pageName;
        this.header = header;
        this.text = text;
        this.bannerFileOne = bannerFileOne;
        this.bannerFileTwo = bannerFileTwo;
        this.bannerFileThree = bannerFileThree;
        this.bannerFileFour = bannerFileFour;
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

    public List<byte[]> getBannerFileOne() {
        return bannerFileOne;
    }

    public void setBannerFileOne(List<byte[]> bannerFileOne) {
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
}