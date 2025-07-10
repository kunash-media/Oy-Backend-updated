package com.oy.oy_jewels.repository;



import com.oy.oy_jewels.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    // Find admin by email
    Optional<AdminEntity> findByEmail(String email);

    // Check if admin exists by email
    boolean existsByEmail(String email);

    // Find admin by name
    Optional<AdminEntity> findByName(String name);

    // Check if admin exists by name
    boolean existsByName(String name);


    // New methods
    Optional<AdminEntity> findByEmailAndPassword(String email, String password);
    List<AdminEntity> findByRole(String role);
}