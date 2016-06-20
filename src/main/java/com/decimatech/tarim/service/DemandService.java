package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Demand;
import com.decimatech.tarim.model.domain.DemandTable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DemandService {

    List<Demand> getAllDemands(Authentication authentication);

    List<Demand> gellAllUnreadDemands(Authentication authentication);

    Demand updateDemand(Demand demand);

    List<DemandTable> getDemandTable(Authentication authentication);

    Demand findOne(Long id);

}
