package com.decimatech.tarim.service;

import com.decimatech.tarim.model.domain.MaintainTable;
import com.decimatech.tarim.model.entity.*;
import com.decimatech.tarim.repository.DemandRepository;
import com.decimatech.tarim.repository.MaintainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("maintainService")
@Transactional
public class MaintainServiceImpl implements MaintainService {

    @Autowired
    private MaintainRepository maintainRepository;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @Override
    public void createMaintain(Maintain maintain) {
        maintainRepository.save(maintain);

    }

    @Override
    public Maintain firstCreate(Long demandId, Long vendorId) {

        Maintain maintain = new Maintain();
        maintain.setDemandId(demandId);
        maintain.setVendorId(vendorId);
        return maintainRepository.save(maintain);
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
        maintain.setUpdateDate(new Date());
        return maintainRepository.save(maintain);
    }

    @Override
    public List<MaintainTable> getMaintainTable(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

        List<Maintain> maintains = new ArrayList<>();
        List<Demand> demands = new ArrayList<>();

        if (isAdmin) {
            maintains = maintainRepository.findAll();
            demands = demandRepository.findAll();
        } else {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            maintains = maintainRepository.findByVendorId(vendor.getVendorId());
            demands = demandRepository.findByVendorIdAndDeletedFalse(vendor.getVendorId());
        }

        List<MaintainTable> maintainTables = new ArrayList<>();

        for (Maintain maintain : maintains) {

            MaintainTable maintainTableRow = new MaintainTable();
            for (Demand demand : demands) {

                if (Objects.equals(demand.getMaintainId(), maintain.getMaintainId())) {
                    maintainTableRow.setMaintainId(maintain.getMaintainId());
                    maintainTableRow.setDemandId(maintain.getDemandId());
                    maintainTableRow.setDemandState(demand.getDemandState());

                    Vendor vendor = vendorService.getVendorByVendorId(maintain.getVendorId());
                    String vendorName = vendor.getVendorName();
                    maintainTableRow.setVendorName(vendorName);

                    City city = cityService.getCityById(demand.getCustomerCity());
                    District district = districtService.getDistrictById(demand.getCustomerDistrict());
                    String customerName = demand.getCustomerName() + " " + demand.getCustomerSurname();

                    maintainTableRow.setCity(city.getCityName());
                    maintainTableRow.setDistrict(district.getDistrictName());
                    maintainTableRow.setCustomerName(customerName);

                    maintainTables.add(maintainTableRow);
                }
            }
        }

        return maintainTables;
    }

    @Override
    public Maintain getMaintainByDemandId(Long id) {
        return maintainRepository.findByDemandId(id);
    }
}
