package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Vendor getVendorByUsername(String username);

}
