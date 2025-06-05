package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.AdminRequestDTO;
import com.oy.oy_jewels.dto.request.AdminUpdateDTO;
import com.oy.oy_jewels.dto.response.AdminResponseDTO;
import com.oy.oy_jewels.entity.AdminEntity;
import com.oy.oy_jewels.mapper.AdminMapper;
import com.oy.oy_jewels.repository.AdminRepository;
import com.oy.oy_jewels.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO) {
        AdminEntity adminEntity = adminMapper.toEntity(adminRequestDTO);
        AdminEntity savedEntity = adminRepository.save(adminEntity);
        return adminMapper.toResponseDTO(savedEntity);
    }

    @Override
    public AdminResponseDTO getAdminById(Long id) {
        Optional<AdminEntity> adminEntity = adminRepository.findById(id);
        return adminEntity.map(adminMapper::toResponseDTO).orElse(null);
    }

    @Override
    public AdminResponseDTO getAdminByEmail(String email) {
        Optional<AdminEntity> adminEntity = adminRepository.findByEmail(email);
        return adminEntity.map(adminMapper::toResponseDTO).orElse(null);
    }

    @Override
    public List<AdminResponseDTO> getAllAdmins() {
        List<AdminEntity> adminEntities = adminRepository.findAll();
        return adminMapper.toResponseDTOList(adminEntities);
    }

    @Override
    public AdminResponseDTO updateAdmin(Long id, AdminUpdateDTO adminUpdateDTO) {
        Optional<AdminEntity> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            AdminEntity adminToUpdate = existingAdmin.get();
            adminMapper.updateEntityFromDTO(adminUpdateDTO, adminToUpdate);
            AdminEntity updatedEntity = adminRepository.save(adminToUpdate);
            return adminMapper.toResponseDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }
}