package com.decimatech.tarim.service;


import com.decimatech.tarim.model.Vendor;

import java.util.List;

public interface VendorService {

    Vendor getVendorByVendorId(Long id);

    Vendor getVendorByUsername(String username);

    List<Vendor> getAllVendors();

    void registerVendor(Vendor vendor);

    Vendor updateVendor(Vendor vendor);
}