package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.entity.AdminEntity;
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

    @Override
    public AdminEntity createAdmin(AdminEntity admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Optional<AdminEntity> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<AdminEntity> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public List<AdminEntity> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public AdminEntity updateAdmin(Long id, AdminEntity admin) {
        Optional<AdminEntity> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            AdminEntity adminToUpdate = existingAdmin.get();
            adminToUpdate.setName(admin.getName());
            adminToUpdate.setEmail(admin.getEmail());
            adminToUpdate.setPassword(admin.getPassword());
            return adminRepository.save(adminToUpdate);
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