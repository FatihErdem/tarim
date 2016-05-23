package com.decimatech.tarim.service;

import com.decimatech.tarim.model.Vendor;
import com.decimatech.tarim.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("vendorService")
@Transactional
public class VendorServiceImpl implements VendorService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor getVendorByVendorId(Long id) {
        return vendorRepository.findOne(id);
    }

    @Override
    public Vendor getVendorByUsername(String username) {
        return vendorRepository.getVendorByUsername(username);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public void registerVendor(Vendor vendor) {
        final String encodedPassword = passwordEncoder.encode(vendor.getPassword());
        vendor.setPassword(encodedPassword);
        vendorRepository.save(vendor);
    }

}
