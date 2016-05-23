package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin getAdminByUsername(String username);
}
