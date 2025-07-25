package com.oy.oy_jewels.dto.request;

import java.math.BigDecimal;

public class ShiprocketOrderItem {
    private String name;
    private String sku;
    private Integer units;
    private BigDecimal selling_price;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    // Removed HSN as it's not required for now

    public ShiprocketOrderItem() {}

    // Getters and Setters
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
}
