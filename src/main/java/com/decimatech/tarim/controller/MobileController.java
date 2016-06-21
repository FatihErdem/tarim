package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.dto.DemandJsonDto;
import com.decimatech.tarim.model.entity.Demand;
import com.decimatech.tarim.model.entity.Vendor;
import com.decimatech.tarim.service.CityService;
import com.decimatech.tarim.service.DemandService;
import com.decimatech.tarim.service.DistrictService;
import com.decimatech.tarim.service.VendorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "mobile")
public class MobileController {

    @Autowired
    private DemandService demandService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @RequestMapping(value = "/getdemands/{vendorname}")

    public List<DemandJsonDto> demandList(@PathVariable("vendorname") String vendorName) throws IOException {

        Vendor vendor = vendorService.getVendorByUsername(vendorName);

        List<Demand> demands = demandService.getInProgressDemandsByVendorId(vendor.getVendorId(), "IN_PROGRESS");

        List<DemandJsonDto> demandJsonDtoList = new ArrayList<>();

        for (Demand demand : demands) {
            DemandJsonDto demandJsonDto = new DemandJsonDto();
            BeanUtils.copyProperties(demand, demandJsonDto);
            demandJsonDto.setCustomerCity(cityService.getCityById(demand.getCustomerCity()).getCityName());
            demandJsonDto.setCustomerDistrict(districtService.getDistrictById(demand.getCustomerDistrict()).getDistrictName());
            demandJsonDtoList.add(demandJsonDto);
        }

        return demandJsonDtoList;

    }
}
