package com.decimatech.tarim.service;

import com.decimatech.tarim.model.Maintain;
import org.jboss.jandex.Main;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface MaintainService {

    void createMaintain(Maintain maintain);

    void firstCreate(Long id, Authentication authentication);

    List<Maintain> getAll(Authentication authentication);

    Maintain findOne(Long id);

    Maintain updateMaintain(Maintain maintain);
}
