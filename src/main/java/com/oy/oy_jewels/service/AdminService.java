package com.oy.oy_jewels.service;


import com.oy.oy_jewels.entity.AdminEntity;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    // Create new admin
    AdminEntity createAdmin(AdminEntity admin);

    // Get admin by ID
    Optional<AdminEntity> getAdminById(Long id);

    // Get admin by email
    Optional<AdminEntity> getAdminByEmail(String email);

    // Get all admins
    List<AdminEntity> getAllAdmins();

    // Update admin
    AdminEntity updateAdmin(Long id, AdminEntity admin);

    // Delete admin by ID
    void deleteAdmin(Long id);

    // Check if admin exists by email
    boolean existsByEmail(String email);
}