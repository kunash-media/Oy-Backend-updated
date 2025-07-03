package com.oy.oy_jewels.dto.response;

public class BannerResponse {
    private Long id;
    private String pageName;
    private String header;
    private String text;
    private byte[] bannerFileOne;
    private byte[] bannerFileTwo;
    private byte[] bannerFileThree;
    private byte[] bannerFileFour;

    // Constructor
    public BannerResponse(Long id, String pageName, String header, String text,
                          byte[] bannerFileOne, byte[] bannerFileTwo,
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

    // Getters (no setters needed for response DTO)
    public Long getId() {
        return id;
    }

    public String getPageName() {
        return pageName;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public byte[] getBannerFileOne() {
        return bannerFileOne;
    }

    public byte[] getBannerFileTwo() {
        return bannerFileTwo;
    }

    public byte[] getBannerFileThree() {
        return bannerFileThree;
    }

    public byte[] getBannerFileFour() {
        return bannerFileFour;
    }
}