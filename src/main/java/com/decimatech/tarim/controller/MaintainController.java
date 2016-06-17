package com.decimatech.tarim.controller;


import com.decimatech.tarim.controller.validation.MaintainFormValidator;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    private MaintainFormValidator formValidator;

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

    @InitBinder("form")
    protected void initBinder(final WebDataBinder binder) {
        binder.setValidator(this.formValidator);
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMaintainList(Authentication authentication, Model model) {

        List<MaintainTable> maintainTableList = maintainService.getMaintainTable(authentication);
        model.addAttribute("maintains", maintainTableList);
        return "maintainList";
    }

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

        if (!isVendor) {
            if (Objects.equals(demand.getDemandState(), "IN_PROGRESS")) {
                model.addAttribute("message", "Şu anda servis aşamasındadır. Bayi servisi tamamlandığında tarafınıza iletilecektir.");
                return "maintainStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "COMPLETED")) {
                model.addAttribute("message", "Bayi servisi tamamlamıştır. Servis raporunu onaylayabilir ya da reddedebilirsiniz.");
                return "maintainStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "REJECTED")) {
                model.addAttribute("message", "Servis raporu güncellenmek üzere bayiye yollanmıştır.");
                return "maintainStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "APPROVED")) {
                model.addAttribute("message", "Servis raporu tarafınızca onaylanıp cari hesaba geçmiştir.");
                return "maintainStaticForm";
            }
        } else {
            if (Objects.equals(demand.getDemandState(), "IN_PROGRESS")) {
                model.addAttribute("message", "Raporu düzenleyip merkeze yollayabilirsiniz.");
                return "maintainUpdateForm";
            } else if (Objects.equals(demand.getDemandState(), "COMPLETED")) {
                model.addAttribute("message", "Raporunuz merkeze iletilmiştir. Merkez raporunuzu inceledikten sonra onaylayacak ya da reddedecektir.");
                return "maintainStaticForm";
            } else if (Objects.equals(demand.getDemandState(), "REJECTED")) {
                model.addAttribute("message", "Raporunuz merkez tarafından reddedilmiştir. Raporunuzu güncelleyip geri yollamanız gerekmektedir.");
                return "maintainUpdateForm";
            } else if (Objects.equals(demand.getDemandState(), "APPROVED")) {
                model.addAttribute("message", "Raporunuz merkez tarafından onaylanmıştır ve cari hesaba aktarılmıştır.");
                return "maintainStaticForm";
            }
        }
        return "maintainStaticForm";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=update")
    public String updateMaintain(@ModelAttribute("form") MaintainFormDto formDto, BindingResult result, @PathVariable("id") Long id, Model model, Authentication authentication) {


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
    public String completeMaintainForm(@Valid @ModelAttribute("form") MaintainFormDto formDto, BindingResult result, @PathVariable("id") Long id, Model
            model, Authentication authentication) {

        Maintain maintain1 = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain1.getDemandId());
        City demandCity = cityService.getCityById(demand.getCustomerCity());
        District demandDistrict = districtService.getDistrictById(demand.getCustomerDistrict());
        List<Machine> machines = machineRepository.findAll();

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
            formDto.setDemand(demand);
            formDto.setMaintain(formDto.getMaintain());
            formDto.getMaintain().setMaintainId(id);
            model.addAttribute("form", formDto);
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

            demand.setCompleted();
            demandService.updateDemand(demand);
            return "redirect:/maintains/details/" + id;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=rejected")
    public String rejectMaintainForm(@ModelAttribute("form") MaintainFormDto formDto, @PathVariable("id") Long id) {

        Maintain maintain1 = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain1.getDemandId());
        demand.setDemandState("REJECTED");
        demandService.updateDemand(demand);

        return "redirect:/maintains/details/" + id;
    }

    @PreAuthorize("hasAuthority('ADMIN') OR  @vendorService.getVendorByUsername(authentication.name).vendorId == @maintainRepository.findOne(#id).vendorId")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST, params = "action=approved")
    public String approveMaintainForm(@ModelAttribute("form") MaintainFormDto formDto, @PathVariable("id") Long id) {

        Maintain maintain1 = maintainService.findOne(id);
        Demand demand = demandService.findOne(maintain1.getDemandId());
        demand.setDemandState("APPROVED");
        demandService.updateDemand(demand);

        return "redirect:/maintains/details/" + id;
    }


}
