package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.Demand;
import com.decimatech.tarim.model.Vendor;
import com.decimatech.tarim.repository.DemandRepository;
import com.decimatech.tarim.service.DemandService;
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
@RequestMapping(value = "/demands")
public class DemandContoller {

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private DemandService demandService;

    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getDemandForm(Model model, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));
        List<Demand> demands = demandService.getAllDemands(authentication);

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }
        Demand demand = new Demand();
        model.addAttribute("demand", demand);
        model.addAttribute("vendors", vendors);
        return "demandForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRequest(@Valid @ModelAttribute Demand demand, BindingResult bindingResult, Model model, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));
        List<Demand> demands = demandService.getAllDemands(authentication);

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("vendors", vendors);
            return "demandForm";
        } else {
            demandRepository.save(demand);
            return "redirect:/demands";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getDemandList(Model model, Authentication authentication) {

        List<Demand> demands = demandService.getAllDemands(authentication);
        List<Vendor> vendors = vendorService.getAllVendors();

        model.addAttribute("vendors", vendors);
        model.addAttribute("demands", demands);
        return "demandList";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @demandRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getDemandDetails(@PathVariable("id") Long id, Model model) {

        Demand demand = demandRepository.findOne(id);
        List<Vendor> vendors = vendorService.getAllVendors();
        model.addAttribute("demand", demand);
        model.addAttribute("vendors", vendors);
        return "demandForm";
    }


}
