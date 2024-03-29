package com.decimatech.tarim.service;

import com.decimatech.tarim.model.domain.DemandTable;
import com.decimatech.tarim.model.entity.Demand;
import com.decimatech.tarim.model.entity.Vendor;
import com.decimatech.tarim.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
     * @return admin ise butun talepler, degilse bayinin talepleri
     **/
    @Override
    public List<Demand> getAllDemands(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        if (isAdmin) {
            return demandRepository.findAll();
        } else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return demandRepository.findByVendorIdAndDeletedFalse(vendor.getVendorId());
        }
    }

    @Override
    public List<Demand> gellAllUnreadDemands(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        if (isAdmin) {
            return demandRepository.findByUnreadAdminTrueAndDeletedFalse();
        } else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return demandRepository.findByVendorIdAndDeletedFalseAndUnreadVendorTrue(vendor.getVendorId());
        }
    }

    @Override
    public Demand updateDemand(Demand demand) {
        demand.setUpdateDate(new Date());
        return demandRepository.save(demand);
    }

    @Override
    public List<DemandTable> getDemandTable(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        List<Demand> demands = new ArrayList<>();

        if (isAdmin) {
            demands = demandRepository.findAll();
        } else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            demands = demandRepository.findByVendorIdAndDeletedFalse(vendor.getVendorId());
        }

        List<Vendor> vendors = vendorService.getAllVendors();
        List<DemandTable> demandTables = new ArrayList<>();

        for (Demand demand : demands) {
            DemandTable demandTableRow = new DemandTable();
            for (Vendor vendor : vendors) {

                if (Objects.equals(demand.getVendorId(), vendor.getVendorId())) {
                    demandTableRow.setVendorName(vendor.getVendorName());
                    demandTableRow.setDemandId(demand.getDemandId());
                    demandTableRow.setDemandState(demand.getDemandState());
                    demandTableRow.setDemandType(demand.getDemandType());
                    break;
                }
            }
            demandTables.add(demandTableRow);
        }
        return demandTables;
    }

    @Override
    public Demand findOne(Long id) {
        return demandRepository.findOne(id);
    }

    @Override
    public Long getDemandCount(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

        if (isAdmin) {
            return demandRepository.count();
        } else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return demandRepository.countByVendorId(vendor.getVendorId());
        }
    }

    @Override
    public Long getInProgresDemandCount(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

        if(isAdmin){
            return demandRepository.countByDemandState("IN_PROGRESS");
        }
        else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return demandRepository.countByDemandStateAndVendorId("IN_PROGRESS", vendor.getVendorId());
        }
    }

    @Override
    public Long getApprovedDemandCount(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

        if(isAdmin){
            return demandRepository.countByDemandState("APPROVED");
        }
        else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            return demandRepository.countByDemandStateAndVendorId("APPROVED", vendor.getVendorId());
        }
    }

    @Override
    public List<Demand> getInProgressDemandsByVendorId(Long vendorId, String demandState) {
        return demandRepository.findByVendorIdAndDemandState(vendorId, demandState);
    }


}
