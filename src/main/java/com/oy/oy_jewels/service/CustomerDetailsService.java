package com.oy.oy_jewels.service;


import com.oy.oy_jewels.entity.CustomerDetailsEntity;

import java.util.List;

public interface CustomerDetailsService {

    CustomerDetailsEntity saveCustomer(CustomerDetailsEntity customer);

    List<CustomerDetailsEntity> getAllCustomers();

    CustomerDetailsEntity getCustomerById(Long userId);

    CustomerDetailsEntity updateCustomer(Long userId, CustomerDetailsEntity customer);

    void deleteCustomerById(Long userId);

    List<CustomerDetailsEntity> getCustomersByStatus(String status);

    CustomerDetailsEntity getCustomerByEmail(String email);

    List<CustomerDetailsEntity> getCustomersByMaritalStatus(String maritalStatus);

    List<CustomerDetailsEntity> searchCustomersByName(String customerName);
}