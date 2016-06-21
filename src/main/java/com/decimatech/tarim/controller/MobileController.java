package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.domain.ResultFromMobile;
import com.decimatech.tarim.model.dto.DemandJsonDto;
import com.decimatech.tarim.model.entity.Demand;
import com.decimatech.tarim.model.entity.Maintain;
import com.decimatech.tarim.model.entity.Vendor;
import com.decimatech.tarim.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private MaintainService maintainService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/getdemands/{vendorname}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/maintainresult", method = RequestMethod.POST)
    public void postMaintain(@RequestBody ResultFromMobile resultFromMobile) {

        System.out.printf(resultFromMobile.toString());

        Maintain maintain = maintainService.findOne(resultFromMobile.getMaintainId());
        List<String> urls = resultFromMobile.getUrls();
        for (String url: urls){
            imageService.saveImagesForMaintain(maintain.getMaintainId(), url);
        }

        maintain.setStartTime(resultFromMobile.getStartTime());
        maintain.setFinishTime(resultFromMobile.getFinishTime());
        maintain.setTotalDistance(resultFromMobile.getTotalDistance());
        maintainService.updateMaintain(maintain);
    }
}
