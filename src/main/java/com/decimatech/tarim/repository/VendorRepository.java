package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.Vendor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface VendorRepository extends Repository<Vendor, Long> {

    Vendor getVendorByUsername(String username);

    Vendor save(Vendor vendor);

    Vendor findOne(Long id);

    List<Vendor> findAll();
}
