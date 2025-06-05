package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CustomerRequestDTO;
import com.oy.oy_jewels.dto.response.CustomerResponseDTO;
import com.oy.oy_jewels.entity.CustomerDetailsEntity;
import com.oy.oy_jewels.mapper.CustomerMapper;
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

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping("/create-customer")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequest) {
        CustomerDetailsEntity customer = customerMapper.toEntity(customerRequest);
        CustomerDetailsEntity savedCustomer = customerDetailsService.saveCustomer(customer);
        CustomerResponseDTO responseDTO = customerMapper.toResponseDTO(savedCustomer);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-All-Customers")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerDetailsEntity> customers = customerDetailsService.getAllCustomers();
        List<CustomerResponseDTO> responseDTOs = customerMapper.toResponseDTOList(customers);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long userId) {
        CustomerDetailsEntity customer = customerDetailsService.getCustomerById(userId);
        if (customer != null) {
            CustomerResponseDTO responseDTO = customerMapper.toResponseDTO(customer);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long userId,
                                                               @RequestBody CustomerRequestDTO customerRequest) {
        CustomerDetailsEntity customer = customerMapper.toEntity(customerRequest);
        CustomerDetailsEntity updatedCustomer = customerDetailsService.updateCustomer(userId, customer);
        if (updatedCustomer != null) {
            CustomerResponseDTO responseDTO = customerMapper.toResponseDTO(updatedCustomer);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long userId) {
        customerDetailsService.deleteCustomerById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersByStatus(@PathVariable String status) {
        List<CustomerDetailsEntity> customers = customerDetailsService.getCustomersByStatus(status);
        List<CustomerResponseDTO> responseDTOs = customerMapper.toResponseDTOList(customers);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByEmail(@PathVariable String email) {
        CustomerDetailsEntity customer = customerDetailsService.getCustomerByEmail(email);
        if (customer != null) {
            CustomerResponseDTO responseDTO = customerMapper.toResponseDTO(customer);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/marital-status/{maritalStatus}")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersByMaritalStatus(@PathVariable String maritalStatus) {
        List<CustomerDetailsEntity> customers = customerDetailsService.getCustomersByMaritalStatus(maritalStatus);
        List<CustomerResponseDTO> responseDTOs = customerMapper.toResponseDTOList(customers);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/search/{customerName}")
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomersByName(@PathVariable String customerName) {
        List<CustomerDetailsEntity> customers = customerDetailsService.searchCustomersByName(customerName);
        List<CustomerResponseDTO> responseDTOs = customerMapper.toResponseDTOList(customers);
        return ResponseEntity.ok(responseDTOs);
    }
}