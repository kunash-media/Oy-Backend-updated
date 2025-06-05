package com.oy.oy_jewels.service;


import com.oy.oy_jewels.dto.request.AdminRequestDTO;
import com.oy.oy_jewels.dto.request.AdminUpdateDTO;
import com.oy.oy_jewels.dto.response.AdminResponseDTO;

import java.util.List;

public interface AdminService {

    // Create new admin
    AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO);

    // Get admin by ID
    AdminResponseDTO getAdminById(Long id);

    // Get admin by email
    AdminResponseDTO getAdminByEmail(String email);

    // Get all admins
    List<AdminResponseDTO> getAllAdmins();

    // Update admin
    AdminResponseDTO updateAdmin(Long id, AdminUpdateDTO adminUpdateDTO);

    // Delete admin by ID
    void deleteAdmin(Long id);

    // Check if admin exists by email
    boolean existsByEmail(String email);
}