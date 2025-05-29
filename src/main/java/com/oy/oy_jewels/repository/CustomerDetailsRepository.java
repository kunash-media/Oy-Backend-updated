package com.oy.oy_jewels.repository;


import com.oy.oy_jewels.entity.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, Long> {

    List<CustomerDetailsEntity> findByStatus(String status);

    CustomerDetailsEntity findByEmail(String email);

    List<CustomerDetailsEntity> findByMaritalStatus(String maritalStatus);

    List<CustomerDetailsEntity> findByCustomerNameContaining(String customerName);
}
