package com.decimatech.tarim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userAdmin = adminService.getAdminByUsername(username);

        if (userAdmin != null) {
            System.out.println(userAdmin.getUsername());
            return userAdmin;
        } else {
            UserDetails userVendor = vendorService.getVendorByUsername(username);
            if (userVendor == null) {
                throw new UsernameNotFoundException("No such user");
            }else{
                System.out.println(userVendor.getUsername());
                return userVendor;
            }
        }
    }
}

