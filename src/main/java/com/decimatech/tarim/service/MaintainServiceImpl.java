package com.decimatech.tarim.service;

import com.decimatech.tarim.model.Maintain;
import com.decimatech.tarim.model.Vendor;
import com.decimatech.tarim.repository.MaintainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.extras.springsecurity4.dialect.processor.AuthenticationAttrProcessor;

import java.util.Collection;
import java.util.List;

@Service("maintainService")
@Transactional
public class MaintainServiceImpl implements MaintainService {

    @Autowired
    private MaintainRepository maintainRepository;

    @Autowired
    private VendorService vendorService;

    @Override
    public void createMaintain(Maintain maintain) {
        maintainRepository.save(maintain);

    }

    @Override
    public void firstCreate(Long id, Authentication authentication) {
        Vendor vendor = vendorService.getVendorByUsername(authentication.getName());

        Maintain maintain = new Maintain();
        maintain.setDemandId(id);
        maintain.setVendorId(vendor.getVendorId());
        maintainRepository.save(maintain);
    }

    @Override
    public List<Maintain> getAll(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        if (isAdmin) {
           return maintainRepository.findAll();
        } else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return maintainRepository.findByVendorId(vendor.getVendorId());
        }
    }

    @Override
    public Maintain findOne(Long id) {
        return maintainRepository.findOne(id);
    }

    @Override
    public Maintain updateMaintain(Maintain maintain) {
        return maintainRepository.save(maintain);
    }
}
