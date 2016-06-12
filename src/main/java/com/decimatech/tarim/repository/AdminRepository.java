package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.Admin;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AdminRepository extends Repository<Admin, Long> {

    Admin getAdminByUsername(String username);

    Admin findOne(Long id);

    List<Admin> findAll();

    void save(Admin admin);
}
