package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Vendor;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UtilityService {

    List<Vendor> getUserVendor(Authentication authentication);

    void expireSessionWithUserId(Long id);

    boolean isVendor(Authentication authentication);
}
