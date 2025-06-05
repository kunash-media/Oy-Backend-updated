package com.oy.oy_jewels.dto.request;


public class ShippingRequestDTO {
    private String shippingTitle1;
    private String shippingDescription1;
    private String shippingTitle2;
    private String shippingDescription2;
    private String shippingTitle3;
    private String shippingDescription3;
    private String shippingTitle4;
    private String shippingDescription4;
    private String shippingTitle5;
    private String shippingDescription5;

    // Default constructor
    public ShippingRequestDTO() {}

    // Parameterized constructor
    public ShippingRequestDTO(String shippingTitle1, String shippingDescription1, String shippingTitle2,
                              String shippingDescription2, String shippingTitle3, String shippingDescription3,
                              String shippingTitle4, String shippingDescription4, String shippingTitle5,
                              String shippingDescription5) {
        this.shippingTitle1 = shippingTitle1;
        this.shippingDescription1 = shippingDescription1;
        this.shippingTitle2 = shippingTitle2;
        this.shippingDescription2 = shippingDescription2;
        this.shippingTitle3 = shippingTitle3;
        this.shippingDescription3 = shippingDescription3;
        this.shippingTitle4 = shippingTitle4;
        this.shippingDescription4 = shippingDescription4;
        this.shippingTitle5 = shippingTitle5;
        this.shippingDescription5 = shippingDescription5;
    }

    // Getters and Setters
    public String getShippingTitle1() {
        return shippingTitle1;
    }

    public void setShippingTitle1(String shippingTitle1) {
        this.shippingTitle1 = shippingTitle1;
    }

    public String getShippingDescription1() {
        return shippingDescription1;
    }

    public void setShippingDescription1(String shippingDescription1) {
        this.shippingDescription1 = shippingDescription1;
    }

    public String getShippingTitle2() {
        return shippingTitle2;
    }

    public void setShippingTitle2(String shippingTitle2) {
        this.shippingTitle2 = shippingTitle2;
    }

    public String getShippingDescription2() {
        return shippingDescription2;
    }

    public void setShippingDescription2(String shippingDescription2) {
        this.shippingDescription2 = shippingDescription2;
    }

    public String getShippingTitle3() {
        return shippingTitle3;
    }

    public void setShippingTitle3(String shippingTitle3) {
        this.shippingTitle3 = shippingTitle3;
    }

    public String getShippingDescription3() {
        return shippingDescription3;
    }

    public void setShippingDescription3(String shippingDescription3) {
        this.shippingDescription3 = shippingDescription3;
    }

    public String getShippingTitle4() {
        return shippingTitle4;
    }

    public void setShippingTitle4(String shippingTitle4) {
        this.shippingTitle4 = shippingTitle4;
    }

    public String getShippingDescription4() {
        return shippingDescription4;
    }

    public void setShippingDescription4(String shippingDescription4) {
        this.shippingDescription4 = shippingDescription4;
    }

    public String getShippingTitle5() {
        return shippingTitle5;
    }

    public void setShippingTitle5(String shippingTitle5) {
        this.shippingTitle5 = shippingTitle5;
    }

    public String getShippingDescription5() {
        return shippingDescription5;
    }

    public void setShippingDescription5(String shippingDescription5) {
        this.shippingDescription5 = shippingDescription5;
    }
}

