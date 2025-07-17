package com.oy.oy_jewels.dto.response;

import java.math.BigDecimal;

public class ShiprocketOrderItem {
    private String name;
    private String sku;
    private Integer units;
    private BigDecimal selling_price;
    private BigDecimal discount;
    private BigDecimal tax;
    private String hsn;

    public ShiprocketOrderItem() {}

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getUnits() { return units; }
    public void setUnits(Integer units) { this.units = units; }

    public BigDecimal getSelling_price() { return selling_price; }
    public void setSelling_price(BigDecimal selling_price) { this.selling_price = selling_price; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }

    public String getHsn() { return hsn; }
    public void setHsn(String hsn) { this.hsn = hsn; }
}