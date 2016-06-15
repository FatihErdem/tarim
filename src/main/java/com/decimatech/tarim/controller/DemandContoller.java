package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.domain.DemandTable;
import com.decimatech.tarim.model.entity.*;
import com.decimatech.tarim.repository.DemandRepository;
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
@RequestMapping(value = "/demands")
public class DemandContoller {

    @Autowired
    private DemandRepository demandRepository;

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
    private ReplacedPartService replacedPartService;

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
        return "demandCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRequest(@Valid @ModelAttribute Demand demand, BindingResult bindingResult, Model model, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        if (bindingResult.hasErrors()) {
            City demandCity = cityService.getCityById(demand.getCustomerCity());
            District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());

            model.addAttribute("city", demandCity);
            model.addAttribute("district", demandDistrict);
            model.addAttribute("vendors", vendors);
            return "demandCreateForm";
        } else {
            demandRepository.save(demand);
            return "redirect:/demands";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getDemandList(Model model, Authentication authentication) {

        List<DemandTable> demands = demandService.getDemandTable(authentication);
        List<Vendor> vendors = vendorService.getAllVendors();

        model.addAttribute("vendors", vendors);
        model.addAttribute("demands", demands);
        return "demandList";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @demandRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getDemandDetails(@PathVariable("id") Long id, Model model, Authentication authentication) {


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        Demand demand = demandRepository.findOne(id);

        City demandCity = cityService.getCityById(demand.getCustomerCity());
        District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());

        if (demand.isUnread()) {
            demandService.setDemandRead(demand);
        }

        model.addAttribute("demand", demand);
        model.addAttribute("vendors", vendors);
        model.addAttribute("city", demandCity);
        model.addAttribute("district", demandDistrict);

        if (!isVendor) {
            if (Objects.equals(demand.getDemandState(), "OPEN")) {
                model.addAttribute("message", "Talep servise iletilmiştir. Servis kabul ya da reddettiğinde tarafınıza bildirim gelecektir.");
                return "demandInProgressForm";
            } else if (Objects.equals(demand.getDemandState(), "IN_PROGRESS")) {
                model.addAttribute("message", "Bu talep servis için kabul edilmiştir. Servis durumunu SERVİSLER menüsü altından takip edebilirsiniz.");
                return "demandInProgressForm";
            } else if (Objects.equals(demand.getDemandState(), "COMPLETED")){
                model.addAttribute("message", "Bu talebin servis işlemi tamamlanmıştır. Servis raporunu görmek için SERVİSLER menüsünü kullanabilirsiniz.");
                return "demandInProgressForm";
            }

        } else {
            if (Objects.equals(demand.getDemandState(), "OPEN")) {
                model.addAttribute("message", "Bu talebi sol menüdeki 'SERVİSLER' kısmından servis haline getirebilirsiniz.");
                return "demandInProgressForm";
            } else if (Objects.equals(demand.getDemandState(), "IN_PROGRESS")) {
                model.addAttribute("message", "Bu talep servis için kabul edilmiştir. Servis işlemlerini SERVİSLER menüsü altından yapabilirsiniz.");
                return "demandInProgressForm";
            } else if (Objects.equals(demand.getDemandState(), "COMPLETED")){
                model.addAttribute("message", "Bu talebin servis işlemi tamamlanmıştır. Servis raporu merkeze iletilmiştir.");
                return "demandInProgressForm";
            }
            else {
                return "demandUpdateForm";
            }
        }
        return "demandInProgressForm";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @demandRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=save")
    public String updateDemand(@PathVariable("id") Long id, @Valid @ModelAttribute Demand demand, BindingResult result, Model model, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }

        if (result.hasErrors()) {
            City demandCity = cityService.getCityById(demand.getCustomerCity());
            District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());
            demand.setDemandId(id);
            model.addAttribute("city", demandCity);
            model.addAttribute("district", demandDistrict);
            model.addAttribute("vendors", vendors);
            return "demandUpdateForm";
        } else {
            demand.setDemandId(id);
            demandService.updateDemand(demand);
            return "redirect:/demands";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @demandRepository.findOne(#demandId).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=openmaintain")
    public String openMaintainFromDemand(@PathVariable("id") Long demandId, @Valid @ModelAttribute Demand demand, BindingResult result, Model model, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));

        List<Vendor> vendors = new ArrayList<>();
        if (isVendor) {
            Vendor vendor = vendorService.getVendorByUsername(authentication.getName());
            vendors.add(vendor);
        } else {
            vendors = vendorService.getAllVendors();
        }


        Maintain maintain = maintainService.firstCreate(demandId, demand.getVendorId());
        replacedPartService.createAllReplacedParts(maintain.getMaintainId());

        demand.setMaintainId(maintain.getMaintainId());
        demand.setInProgress();
        demand.setDemandId(demandId);
        demandService.updateDemand(demand);

        return "redirect:/demands/details/" + demandId;
    }

}
