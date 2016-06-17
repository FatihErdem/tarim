package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private VendorService vendorService;

    /**
     * Bu method gelen authority'e gore Vendor dondurur
     * <p>
     * Gelen authentication yapisina gore eger kullanici admin ise
     * butun vendorlari, degilse sadece kendi vendorunu dondurur.
     *
     * @param authentication controller'a gelen talebi yapan user yapisi
     * @return admin ise butun vendorlar, degilse user'in vendoru
     **/
    public List<Vendor> getUserVendor(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }
        return vendors;
    }

}
