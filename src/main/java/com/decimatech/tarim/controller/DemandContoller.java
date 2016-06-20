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

    @Autowired
    private UtilityService utilityService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getDemandForm(Model model, Authentication authentication) {

        List<Vendor> vendors = utilityService.getUserVendor(authentication);

        Demand demand = new Demand();
        model.addAttribute("demand", demand);
        model.addAttribute("vendors", vendors);
        return "demandCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRequest(@Valid @ModelAttribute Demand demand, BindingResult bindingResult, Model model, Authentication authentication) {

        List<Vendor> vendors = utilityService.getUserVendor(authentication);

        if (bindingResult.hasErrors()) {
            City demandCity = cityService.getCityById(demand.getCustomerCity());
            District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());

            model.addAttribute("city", demandCity);
            model.addAttribute("district", demandDistrict);
            model.addAttribute("vendors", vendors);
            return "demandCreateForm";
        } else {
            if (utilityService.isVendor(authentication)) {
                demand.setUnreadAdmin(true);
                demand.setUnreadVendor(false);
            } else {
                demand.setUnreadAdmin(false);
                demand.setUnreadVendor(true);
            }
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

        List<Vendor> vendors = utilityService.getUserVendor(authentication);

        Demand demand = demandRepository.findOne(id);
        City demandCity = cityService.getCityById(demand.getCustomerCity());
        District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());

        if (utilityService.isVendor(authentication) && Objects.equals(demand.getDemandState(), "OPEN")) {
            demand.setUnreadVendor(false);
        } else if (!utilityService.isVendor(authentication) && Objects.equals(demand.getDemandState(), "OPEN")) {
            demand.setUnreadAdmin(false);
        }

        demandService.updateDemand(demand);

        model.addAttribute("demand", demand);
        model.addAttribute("vendors", vendors);
        model.addAttribute("city", demandCity);
        model.addAttribute("district", demandDistrict);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));

        if (isAdmin) {
            if (Objects.equals(demand.getDemandState(), "OPEN")) {
                model.addAttribute("message", "Talep servise iletilmiştir. Servis kabul ya da reddettiğinde tarafınıza bildirim gelecektir.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "IN_PROGRESS")) {
                model.addAttribute("message", "Bu talep servis için kabul edilmiştir. Servis durumunu SERVİSLER menüsü altından takip edebilirsiniz.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "COMPLETED")) {
                model.addAttribute("message", "Bu talebin servis işlemi tamamlanmıştır. Servis raporunu görmek için SERVİSLER menüsünü kullanabilirsiniz.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "REJECTED")) {
                model.addAttribute("message", "Bu talebin servis raporu reddedilmiştir. Servis durumunu SERVİSLER menüsü altından takip edebilirsiniz.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "APPROVED")) {
                model.addAttribute("message", "Bu talebin servis raporu kabul edilmiştir ve cari hesaba aktarılmıştır.");
                return "demandStaticForm";
            }
        } else {
            if (Objects.equals(demand.getDemandState(), "OPEN")) {
                return "demandUpdateForm";
            } else if (Objects.equals(demand.getDemandState(), "IN_PROGRESS")) {
                model.addAttribute("message", "Bu talep servis için kabul edilmiştir. Servis işlemlerini SERVİSLER menüsü altından yapabilirsiniz.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "COMPLETED")) {
                model.addAttribute("message", "Bu talebin servis işlemi tamamlanmıştır. Servis raporu merkeze iletilmiştir.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "REJECTED")) {
                model.addAttribute("message", "Bu talebin servis raporu reddedilmiştir. Raporu düzenlemek için SERVİSLER menüsünü kullanabilirsiniz.");
                return "demandStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "APPROVED")) {
                model.addAttribute("message", "Bu talebin servis raporu kabul edilmiştir ve cari hesaba aktarılmıştır.");
                return "demandStaticForm";
            }
        }
        return "demandStaticForm";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @demandRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=save")
    public String updateDemand(@PathVariable("id") Long id, @Valid @ModelAttribute Demand demand, BindingResult result, Model model, Authentication authentication) {

        List<Vendor> vendors = utilityService.getUserVendor(authentication);

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

        List<Vendor> vendors = utilityService.getUserVendor(authentication);

        if (result.hasErrors()) {
            City demandCity = cityService.getCityById(demand.getCustomerCity());
            District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());
            demand.setDemandId(demandId);
            model.addAttribute("city", demandCity);
            model.addAttribute("district", demandDistrict);
            model.addAttribute("vendors", vendors);
            return "demandUpdateForm";
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
