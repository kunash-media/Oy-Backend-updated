package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.AdminRequestDTO;
import com.oy.oy_jewels.dto.request.AdminUpdateDTO;
import com.oy.oy_jewels.dto.response.AdminResponseDTO;
import com.oy.oy_jewels.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Create new admin
    @PostMapping("/create-admin")
    public ResponseEntity<AdminResponseDTO> createAdmin( @RequestBody AdminRequestDTO adminRequestDTO) {
        AdminResponseDTO createdAdmin = adminService.createAdmin(adminRequestDTO);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    // Get admin by ID
    @GetMapping("get-admin/{id}")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable Long id) {
        AdminResponseDTO admin = adminService.getAdminById(id);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get admin by email
    @GetMapping("/email/{email}")
    public ResponseEntity<AdminResponseDTO> getAdminByEmail(@PathVariable String email) {
        AdminResponseDTO admin = adminService.getAdminByEmail(email);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all admins
    @GetMapping("/get-all-admins")
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
        List<AdminResponseDTO> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // Update admin
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateAdmin(@PathVariable Long id,
                                                         @RequestBody AdminUpdateDTO adminUpdateDTO) {
        AdminResponseDTO updatedAdmin = adminService.updateAdmin(id, adminUpdateDTO);
        if (updatedAdmin != null) {
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete admin
    @DeleteMapping("delete-admin/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        AdminResponseDTO admin = adminService.getAdminById(id);
        if (admin != null) {
            adminService.deleteAdmin(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Check if admin exists by email
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = adminService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}