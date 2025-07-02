package com.oy.oy_jewels.dto.request;

public class ShiprocketOrderItem {
    private String name;
    private String sku;
    private Integer units;
    private Double selling_price;
    private Double discount;
    private String tax;
    private String hsn;

    public ShiprocketOrderItem() {}

    public ShiprocketOrderItem(String name, String sku, Integer units, Double selling_price, Double discount, String tax, String hsn) {
        this.name = name;
        this.sku = sku;
        this.units = units;
        this.selling_price = selling_price;
        this.discount = discount;
        this.tax = tax;
        this.hsn = hsn;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getUnits() { return units; }
    public void setUnits(Integer units) { this.units = units; }

    public Double getSelling_price() { return selling_price; }
    public void setSelling_price(Double selling_price) { this.selling_price = selling_price; }

    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }

    public String getTax() { return tax; }
    public void setTax(String tax) { this.tax = tax; }

    public String getHsn() { return hsn; }
    public void setHsn(String hsn) { this.hsn = hsn; }
}