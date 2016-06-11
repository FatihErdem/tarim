package com.decimatech.tarim.controller;


import com.decimatech.tarim.model.*;
import com.decimatech.tarim.repository.CityRepository;
import com.decimatech.tarim.repository.DistrictRepository;
import com.decimatech.tarim.repository.MachineRepository;
import com.decimatech.tarim.service.DemandService;
import com.decimatech.tarim.service.MaintainService;
import com.decimatech.tarim.service.VendorService;
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

@Controller
@RequestMapping(value = "/maintains")
public class MaintainController {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MaintainService maintainService;

    @Autowired
    private DemandService demandService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMaintainList(Authentication authentication, Model model) {

        List<Maintain> maintains = maintainService.getAll(authentication);
        model.addAttribute("maintains", maintains);
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

        City demandCity = cityRepository.findByCityId(demand.getCustomerCity());
        District demandDistrict = districtRepository.findByDistrictId(demand.getCustomerDistrict());

        model.addAttribute("machines", machines);
        model.addAttribute("city", demandCity);
        model.addAttribute("district", demandDistrict);
        model.addAttribute("vendors", vendors);
        model.addAttribute("maintain", maintain);
        model.addAttribute("demand", demand);
        return "maintainUpdateForm";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=update")
    public String updateMaintain(@Valid @ModelAttribute Maintain maintain, BindingResult result, @PathVariable("id") Long id, Model model, Authentication authentication) {


        Maintain maintain1 = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain1.getDemandId());
        City demandCity = cityRepository.findByCityId(demand.getCustomerCity());
        District demandDistrict = districtRepository.findByDistrictId(demand.getCustomerDistrict());
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
            model.addAttribute("maintain", maintain);
            model.addAttribute("demand", demand);
            return "maintainUpdateForm";
        } else {
            maintain.setDemandId(maintain1.getDemandId());
            maintain.setVendorId(maintain1.getVendorId());
            maintain.setMaintainId(id);
            maintainService.updateMaintain(maintain);
            return "redirect:/maintains";
        }
    }

}
