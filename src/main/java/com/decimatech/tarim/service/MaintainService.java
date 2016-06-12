package com.decimatech.tarim.service;

import com.decimatech.tarim.model.domain.MaintainTable;
import com.decimatech.tarim.model.entity.Maintain;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface MaintainService {

    void createMaintain(Maintain maintain);

    Maintain firstCreate(Long demandId, Long vendorId);

    List<Maintain> getAll(Authentication authentication);

    Maintain findOne(Long id);

    Maintain updateMaintain(Maintain maintain);

    List<MaintainTable> getMaintainTable(Authentication authentication);
}
