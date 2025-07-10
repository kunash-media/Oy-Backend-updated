package com.oy.oy_jewels.service;


import com.oy.oy_jewels.dto.request.AdminLoginDTO;
import com.oy.oy_jewels.dto.request.AdminRequestDTO;
import com.oy.oy_jewels.dto.request.AdminUpdateDTO;
import com.oy.oy_jewels.dto.response.AdminResponseDTO;

import java.util.List;

public interface AdminService {

    // Existing methods
    AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO);
    AdminResponseDTO getAdminById(Long id);
    AdminResponseDTO getAdminByEmail(String email);
    List<AdminResponseDTO> getAllAdmins();
    AdminResponseDTO updateAdmin(Long id, AdminUpdateDTO adminUpdateDTO);
    void deleteAdmin(Long id);
    boolean existsByEmail(String email);

    // New methods
    AdminResponseDTO authenticateAdmin(AdminLoginDTO adminLoginDTO);
    List<AdminResponseDTO> getAdminsByRole(String role);
}