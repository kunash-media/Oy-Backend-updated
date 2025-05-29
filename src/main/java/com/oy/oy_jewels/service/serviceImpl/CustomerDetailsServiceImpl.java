package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.entity.CustomerDetailsEntity;
import com.oy.oy_jewels.repository.CustomerDetailsRepository;
import com.oy.oy_jewels.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Override
    public CustomerDetailsEntity saveCustomer(CustomerDetailsEntity customer) {
        return customerDetailsRepository.save(customer);
    }

    @Override
    public List<CustomerDetailsEntity> getAllCustomers() {
        return customerDetailsRepository.findAll();
    }

    @Override
    public CustomerDetailsEntity getCustomerById(Long userId) {
        return customerDetailsRepository.findById(userId).orElse(null);
    }

    @Override
    public CustomerDetailsEntity updateCustomer(Long userId, CustomerDetailsEntity customer) {
        CustomerDetailsEntity existingCustomer = customerDetailsRepository.findById(userId).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setMobileNumber(customer.getMobileNumber());
            existingCustomer.setMaritalStatus(customer.getMaritalStatus());
            existingCustomer.setBirthDate(customer.getBirthDate());
            existingCustomer.setAnniversary(customer.getAnniversary());
            existingCustomer.setStatus(customer.getStatus());
            return customerDetailsRepository.save(existingCustomer);
        }
        return null;
    }

    @Override
    public void deleteCustomerById(Long userId) {
        customerDetailsRepository.deleteById(userId);
    }

    @Override
    public List<CustomerDetailsEntity> getCustomersByStatus(String status) {
        return customerDetailsRepository.findByStatus(status);
    }

    @Override
    public CustomerDetailsEntity getCustomerByEmail(String email) {
        return customerDetailsRepository.findByEmail(email);
    }

    @Override
    public List<CustomerDetailsEntity> getCustomersByMaritalStatus(String maritalStatus) {
        return customerDetailsRepository.findByMaritalStatus(maritalStatus);
    }

    @Override
    public List<CustomerDetailsEntity> searchCustomersByName(String customerName) {
        return customerDetailsRepository.findByCustomerNameContaining(customerName);
    }
}