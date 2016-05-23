package com.decimatech.tarim.service;

import com.decimatech.tarim.model.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdminByVendorId(Long id);

    Admin getAdminByUsername(String username);

    List<Admin> getAllAdmins();

    void registerAdmin(Admin admin);
}
