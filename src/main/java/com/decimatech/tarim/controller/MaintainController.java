package com.decimatech.tarim.controller;


import com.decimatech.tarim.model.domain.MaintainTable;
import com.decimatech.tarim.model.dto.MaintainFormDto;
import com.decimatech.tarim.model.entity.*;
import com.decimatech.tarim.repository.MachineRepository;
import com.decimatech.tarim.repository.ReplacedPartRepository;
import com.decimatech.tarim.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/maintains")
public class MaintainController {

    public final static int REPLACED_PART_COUNT = 7;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MaintainService maintainService;

    @Autowired
    private DemandService demandService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ReplacedPartRepository replacedPartRepository;

    @Autowired
    private ReplacedPartService replacedPartService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMaintainList(Authentication authentication, Model model) {

       List<MaintainTable> maintainTableList = maintainService.getMaintainTable(authentication);

        model.addAttribute("maintains", maintainTableList);
        return "maintainList";
    }

//    @RequestMapping(value = "/create", method = RequestMethod.GET)
//    public String getCreateForm(@ModelAttribute Maintain maintain, Model model) {
//        List<Machine> machines = machineRepository.findAll();
//        model.addAttribute("machines", machines);
//
//        return "maintainForm";
//    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public String createMaintain(@Valid @ModelAttribute Maintain maintain, BindingResult result, Model model) {
//
//        if (result.hasErrors()) {
//            List<Machine> machines = machineRepository.findAll();
//            model.addAttribute("machines", machines);
//            return "maintainForm";
//        } else {
//            maintainService.createMaintain(maintain);
//            return "redirect:/";
//        }
//    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getMaintainDetails(@PathVariable("id") Long id, Model model, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        List<Machine> machines = machineRepository.findAll();
        Maintain maintain = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain.getDemandId());

        City demandCity = cityService.getCityById(demand.getCustomerCity());
        District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());


        List<ReplacedPart> replacedPartList = replacedPartRepository.findByMaintainIdOrderByReplacedPartIdAsc(maintain.getMaintainId());

        int eksik = REPLACED_PART_COUNT - replacedPartList.size();

        for (int i = 0; i < eksik; i++) {
            ReplacedPart replacedPart = new ReplacedPart();
            replacedPartList.add(replacedPart);
        }

        MaintainFormDto formDto = new MaintainFormDto(maintain, demand, replacedPartList);
        model.addAttribute("form", formDto);
        model.addAttribute("machines", machines);
        model.addAttribute("city", demandCity);
        model.addAttribute("district", demandDistrict);
        model.addAttribute("vendors", vendors);

        if (Objects.equals(demand.getDemandState(), "COMPLETED")) {
            return "maintainCompletedForm";
        } else {

            return "maintainUpdateForm";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=update")
    public String updateMaintain(@Valid @ModelAttribute("form") MaintainFormDto formDto, BindingResult result, @PathVariable("id") Long id, Model model, Authentication authentication) {


        Maintain maintain1 = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain1.getDemandId());
        City demandCity = cityService.getCityById(demand.getCustomerCity());
        District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Machine> machines = machineRepository.findAll();


        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));
        List<Vendor> vendors = new ArrayList<>();

        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        if (result.hasErrors()) {
            model.addAttribute("machines", machines);
            model.addAttribute("city", demandCity);
            model.addAttribute("district", demandDistrict);
            model.addAttribute("vendors", vendors);
            return "maintainUpdateForm";
        } else {

            List<ReplacedPart> replacedPartList = formDto.getReplacedPartList();

            replacedPartService.updateAllReplacedParts(replacedPartList, maintain1.getMaintainId());

            Maintain maintain = formDto.getMaintain();
            maintain.setDemandId(maintain1.getDemandId());
            maintain.setVendorId(maintain1.getVendorId());
            maintain.setMaintainId(id);
            maintainService.updateMaintain(maintain);
            return "redirect:/maintains";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=completed")
    public String completeMaintainForm(@Valid @ModelAttribute("form") MaintainFormDto formDto, BindingResult result, @PathVariable("id") Long id, Model model, Authentication authentication) {

        Maintain maintain1 = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain1.getDemandId());
        City demandCity = cityService.getCityById(demand.getCustomerCity());
        District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Machine> machines = machineRepository.findAll();


        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));
        List<Vendor> vendors = new ArrayList<>();

        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        if (result.hasErrors()) {
            model.addAttribute("machines", machines);
            model.addAttribute("city", demandCity);
            model.addAttribute("district", demandDistrict);
            model.addAttribute("vendors", vendors);
            return "maintainUpdateForm";
        } else {


            demand.setCompleted();
            demandService.updateDemand(demand);

            return "maintainCompletedForm";
        }
    }

}
