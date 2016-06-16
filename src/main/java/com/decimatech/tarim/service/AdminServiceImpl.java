package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Admin;
import com.decimatech.tarim.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    private final static String ADMIN_USERNAME = "decimatechadmin";
    private final static String ADMIN_PASSWORD = "hmzefsa06ar6557";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin getAdminByUsername(String username) {
        return adminRepository.getAdminByUsername(username);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void registerAdmin(Admin admin) {

        final String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        adminRepository.save(admin);

    }

    @Override
    public void createFirstAdmin() {
        Admin admin = new Admin();
        admin.setUsername(ADMIN_USERNAME);
        admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        adminRepository.save(admin);
    }

    @Override
    public boolean ifFirstAdminExist() {
        Admin admin = adminRepository.getAdminByUsername("decimatechadmin");
        return admin != null;
    }

    @Override
    public void updateAdmin(Admin admin) {
        final String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        adminRepository.save(admin);
    }

    @Override
    public Admin getAdminByAdminId(Long id) {
        return adminRepository.findOne(id);
    }
}
