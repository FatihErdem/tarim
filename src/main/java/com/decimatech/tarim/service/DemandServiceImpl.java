package com.decimatech.tarim.service;

import com.decimatech.tarim.model.Demand;
import com.decimatech.tarim.model.Vendor;
import com.decimatech.tarim.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service("demandService")
@Transactional
public class DemandServiceImpl implements DemandService {

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private VendorService vendorService;

    /**
     * Bu method tablolama yapmak icin butun talepleri listeler.
     * <p>
     * Gelen authentication yapisina gore eger kullanici admin ise
     * butun talepleri, degilse sadece kendi bayisine atanmis talepleri listeyebilir.
     *
     * @param authentication controller'a gelen talebi yapan user yapisi
     * @return              admin ise butun talepler, degilse bayinin talepleri
    **/
    @Override
    public List<Demand> getAllDemands(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        if(isAdmin){
            return demandRepository.findAll();
        }else{
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return demandRepository.findByVendorIdAndDeletedFalse(vendor.getVendorId());
        }
    }
}
