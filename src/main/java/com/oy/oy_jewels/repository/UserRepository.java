package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByMobile(String mobile);

    // Find users by status (active/inactive)
    List<UserEntity> findByStatus(String status);

    // Find user by email
    UserEntity findByEmail(String email);

    // Find users by marital status
    List<UserEntity> findByMaritalStatus(String maritalStatus);

    // Find users by customer name containing
    List<UserEntity> findByCustomerNameContainingIgnoreCase(String customerName);


}


