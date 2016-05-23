package com.decimatech.tarim.service;

import com.decimatech.tarim.model.Demand;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DemandService {

    List<Demand> getAllDemands(Authentication authentication);
}
