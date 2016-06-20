package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private SessionRegistry sessionRegistry;

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


    /**
     * Bu method gelen kullanici id'sine gore o kullanicinin butun sessionlarini
     * expire eder. Bu sayede o kullanici sistemden force logout edilmis olur.
     * <p>
     *
     * Bu asamada sadece Vendor sistemden logout edilebiliyor. Ileride lazim olursa
     * diger kullanici modelleri icin de guncellenebilir.
     *
     * @param id sessionlari expire edilecek kullanicinin ıd'si
     **/
    @Override
    public void expireSessionWithUserId(Long id) {

        List<SessionInformation> activeSessions = new ArrayList<>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            activeSessions.addAll(sessionRegistry.getAllSessions(principal, false));
        }

        for (SessionInformation sessionInformation : activeSessions) {
            try {
                Vendor vendor = (Vendor) sessionInformation.getPrincipal();
                if (Objects.equals(vendor.getVendorId(), id)) {
                    SessionInformation vendorSession = sessionRegistry.getSessionInformation(sessionInformation.getSessionId());
                    vendorSession.expireNow();
                }
            } catch (ClassCastException e) {
                System.out.println("ADMIN CAST EDILEMEDI!!!");
            }

        }
    }

    @Override
    public boolean isVendor(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("VENDOR"));
    }

}
