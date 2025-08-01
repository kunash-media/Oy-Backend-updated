package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.bcrypt.BcryptEncoderConfig;
import com.oy.oy_jewels.dto.request.AdminLoginDTO;
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

    private final BcryptEncoderConfig passwordEncoder;

    public AdminServiceImpl(BcryptEncoderConfig passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO) {
        AdminEntity adminEntity = adminMapper.toEntity(adminRequestDTO);

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(adminRequestDTO.getPassword());
        adminEntity.setPassword(hashedPassword);

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

            // If password is being updated, hash it
            if (adminUpdateDTO.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(adminUpdateDTO.getPassword());
                adminUpdateDTO.setPassword(hashedPassword);
            }

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

    @Override
    public AdminResponseDTO authenticateAdmin(AdminLoginDTO adminLoginDTO) {
        Optional<AdminEntity> adminEntity = adminRepository.findByEmail(adminLoginDTO.getEmail());

        if (adminEntity.isPresent()) {
            AdminEntity admin = adminEntity.get();

            // Verify password using BCrypt
            if (passwordEncoder.matches(adminLoginDTO.getPassword(), admin.getPassword())) {
                return adminMapper.toResponseDTO(admin);
            }
        }
        return null;
    }

    @Override
    public List<AdminResponseDTO> getAdminsByRole(String role) {
        List<AdminEntity> adminEntities = adminRepository.findByRole(role);
        return adminMapper.toResponseDTOList(adminEntities);
    }
}