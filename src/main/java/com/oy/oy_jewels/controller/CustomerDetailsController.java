package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.CustomerDetailsEntity;
import com.oy.oy_jewels.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerDetailsController {

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @PostMapping("/create-customer")
    public ResponseEntity<CustomerDetailsEntity> createCustomer(@RequestBody CustomerDetailsEntity customer) {
        CustomerDetailsEntity savedCustomer = customerDetailsService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/get-All-Customers")
    public ResponseEntity<List<CustomerDetailsEntity>> getAllCustomers() {
        List<CustomerDetailsEntity> customers = customerDetailsService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerDetailsEntity> getCustomerById(@PathVariable Long userId) {
        CustomerDetailsEntity customer = customerDetailsService.getCustomerById(userId);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CustomerDetailsEntity> updateCustomer(@PathVariable Long userId,
                                                                @RequestBody CustomerDetailsEntity customer) {
        CustomerDetailsEntity updatedCustomer = customerDetailsService.updateCustomer(userId, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long userId) {
        customerDetailsService.deleteCustomerById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerDetailsEntity>> getCustomersByStatus(@PathVariable String status) {
        List<CustomerDetailsEntity> customers = customerDetailsService.getCustomersByStatus(status);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDetailsEntity> getCustomerByEmail(@PathVariable String email) {
        CustomerDetailsEntity customer = customerDetailsService.getCustomerByEmail(email);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/marital-status/{maritalStatus}")
    public ResponseEntity<List<CustomerDetailsEntity>> getCustomersByMaritalStatus(@PathVariable String maritalStatus) {
        List<CustomerDetailsEntity> customers = customerDetailsService.getCustomersByMaritalStatus(maritalStatus);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/{customerName}")
    public ResponseEntity<List<CustomerDetailsEntity>> searchCustomersByName(@PathVariable String customerName) {
        List<CustomerDetailsEntity> customers = customerDetailsService.searchCustomersByName(customerName);
        return ResponseEntity.ok(customers);
    }
}