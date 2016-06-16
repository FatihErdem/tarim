package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdminByUsername(String username);

    List<Admin> getAllAdmins();

    void registerAdmin(Admin admin);

    void createFirstAdmin();

    boolean ifFirstAdminExist();

    void updateAdmin(Admin admin);

    Admin getAdminByAdminId(Long id);
}
