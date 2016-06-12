package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.Vendor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface VendorRepository extends Repository<Vendor, Long> {

    @Cacheable("vendorByUsername")
    Vendor getVendorByUsername(String username);

    @CacheEvict(value = {"vendorByUsername", "vendor", "vendors"}, allEntries = true)
    Vendor save(Vendor vendor);

    @Cacheable("vendor")
    Vendor findOne(Long id);

    @Cacheable("vendors")
    List<Vendor> findAll();
}
