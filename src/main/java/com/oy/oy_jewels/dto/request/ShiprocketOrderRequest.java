package com.oy.oy_jewels.dto.request;

import java.util.List;

public class ShiprocketOrderRequest {
    private String order_id;
    private String order_date;
    private String pickup_location;
    private String channel_id;
    private String comment;
    private String billing_customer_name;
    private String billing_last_name;
    private String billing_address;
    private String billing_address_2;
    private String billing_city;
    private String billing_pincode;
    private String billing_state;
    private String billing_country;
    private String billing_email;
    private String billing_phone;
    private String shipping_is_billing;
    private String shipping_customer_name;
    private String shipping_last_name;
    private String shipping_address;
    private String shipping_address_2;
    private String shipping_city;
    private String shipping_pincode;
    private String shipping_state;
    private String shipping_country;
    private String shipping_email;
    private String shipping_phone;
    private List<ShiprocketOrderItem> order_items;
    private String payment_method;
    private Double shipping_charges;
    private Double giftwrap_charges;
    private Double transaction_charges;
    private Double total_discount;
    private Double sub_total;
    private Integer length;
    private Integer breadth;
    private Integer height;
    private Double weight;

    // Constructors
    public ShiprocketOrderRequest() {}

    // Getters and Setters
    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getOrder_date() { return order_date; }
    public void setOrder_date(String order_date) { this.order_date = order_date; }

    public String getPickup_location() { return pickup_location; }
    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }

    public String getChannel_id() { return channel_id; }
    public void setChannel_id(String channel_id) { this.channel_id = channel_id; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getBilling_customer_name() { return billing_customer_name; }
    public void setBilling_customer_name(String billing_customer_name) { this.billing_customer_name = billing_customer_name; }

    public String getBilling_last_name() { return billing_last_name; }
    public void setBilling_last_name(String billing_last_name) { this.billing_last_name = billing_last_name; }

    public String getBilling_address() { return billing_address; }
    public void setBilling_address(String billing_address) { this.billing_address = billing_address; }

    public String getBilling_address_2() { return billing_address_2; }
    public void setBilling_address_2(String billing_address_2) { this.billing_address_2 = billing_address_2; }

    public String getBilling_city() { return billing_city; }
    public void setBilling_city(String billing_city) { this.billing_city = billing_city; }

    public String getBilling_pincode() { return billing_pincode; }
    public void setBilling_pincode(String billing_pincode) { this.billing_pincode = billing_pincode; }

    public String getBilling_state() { return billing_state; }
    public void setBilling_state(String billing_state) { this.billing_state = billing_state; }

    public String getBilling_country() { return billing_country; }
    public void setBilling_country(String billing_country) { this.billing_country = billing_country; }

    public String getBilling_email() { return billing_email; }
    public void setBilling_email(String billing_email) { this.billing_email = billing_email; }

    public String getBilling_phone() { return billing_phone; }
    public void setBilling_phone(String billing_phone) { this.billing_phone = billing_phone; }

    public String getShipping_is_billing() { return shipping_is_billing; }
    public void setShipping_is_billing(String shipping_is_billing) { this.shipping_is_billing = shipping_is_billing; }

    public String getShipping_customer_name() { return shipping_customer_name; }
    public void setShipping_customer_name(String shipping_customer_name) { this.shipping_customer_name = shipping_customer_name; }

    public String getShipping_last_name() { return shipping_last_name; }
    public void setShipping_last_name(String shipping_last_name) { this.shipping_last_name = shipping_last_name; }

    public String getShipping_address() { return shipping_address; }
    public void setShipping_address(String shipping_address) { this.shipping_address = shipping_address; }

    public String getShipping_address_2() { return shipping_address_2; }
    public void setShipping_address_2(String shipping_address_2) { this.shipping_address_2 = shipping_address_2; }

    public String getShipping_city() { return shipping_city; }
    public void setShipping_city(String shipping_city) { this.shipping_city = shipping_city; }

    public String getShipping_pincode() { return shipping_pincode; }
    public void setShipping_pincode(String shipping_pincode) { this.shipping_pincode = shipping_pincode; }

    public String getShipping_state() { return shipping_state; }
    public void setShipping_state(String shipping_state) { this.shipping_state = shipping_state; }

    public String getShipping_country() { return shipping_country; }
    public void setShipping_country(String shipping_country) { this.shipping_country = shipping_country; }

    public String getShipping_email() { return shipping_email; }
    public void setShipping_email(String shipping_email) { this.shipping_email = shipping_email; }

    public String getShipping_phone() { return shipping_phone; }
    public void setShipping_phone(String shipping_phone) { this.shipping_phone = shipping_phone; }

    public List<ShiprocketOrderItem> getOrder_items() { return order_items; }
    public void setOrder_items(List<ShiprocketOrderItem> order_items) { this.order_items = order_items; }

    public String getPayment_method() { return payment_method; }
    public void setPayment_method(String payment_method) { this.payment_method = payment_method; }

    public Double getShipping_charges() { return shipping_charges; }
    public void setShipping_charges(Double shipping_charges) { this.shipping_charges = shipping_charges; }

    public Double getGiftwrap_charges() { return giftwrap_charges; }
    public void setGiftwrap_charges(Double giftwrap_charges) { this.giftwrap_charges = giftwrap_charges; }

    public Double getTransaction_charges() { return transaction_charges; }
    public void setTransaction_charges(Double transaction_charges) { this.transaction_charges = transaction_charges; }

    public Double getTotal_discount() { return total_discount; }
    public void setTotal_discount(Double total_discount) { this.total_discount = total_discount; }

    public Double getSub_total() { return sub_total; }
    public void setSub_total(Double sub_total) { this.sub_total = sub_total; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    public Integer getBreadth() { return breadth; }
    public void setBreadth(Integer breadth) { this.breadth = breadth; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}