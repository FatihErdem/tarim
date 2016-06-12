package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.entity.City;
import com.decimatech.tarim.model.entity.District;
import com.decimatech.tarim.model.entity.Vendor;
import com.decimatech.tarim.service.CityService;
import com.decimatech.tarim.service.DistrictService;
import com.decimatech.tarim.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/vendors")
public class VendorController {

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getVendorCreateForm(@ModelAttribute Vendor vendor) {
        return "admin/vendorCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createVendor(@Valid @ModelAttribute Vendor vendor, BindingResult result, Model model) {

        if (result.hasErrors()) {

            City vendorCity = cityService.getCityById(vendor.getVendorCity());
            District vendorDistrict = districtService.getDistrictById(vendor.getVendorDistrict());

            model.addAttribute("vendor", vendor);
            model.addAttribute("city", vendorCity);
            model.addAttribute("district", vendorDistrict);
            return "admin/vendorCreateForm";

        } else {
            vendorService.registerVendor(vendor);
            return "redirect:/vendors";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getVendorList(Model model) {
        List<Vendor> vendors = vendorService.getAllVendors();

        model.addAttribute("vendors", vendors);
        return "admin/vendorList";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getVendorDetails(@PathVariable("id") Long id, Model model) {

        Vendor vendor = vendorService.getVendorByVendorId(id);

        City vendorCity = cityService.getCityById(vendor.getVendorCity());
        District vendorDistrict = districtService.getDistrictById(vendor.getVendorDistrict());

        model.addAttribute("vendor", vendor);
        model.addAttribute("city", vendorCity);
        model.addAttribute("district", vendorDistrict);
        return "admin/vendorUpdateForm";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST)
    public String updateVendor(@PathVariable("id") Long id, @Valid @ModelAttribute Vendor vendor, BindingResult result, Model model) {
        if (result.hasErrors()) {

            if (Objects.equals(vendor.getPassword(), "")) {
                Vendor oldVendor = vendorService.getVendorByVendorId(id);
                vendor.setVendorId(id);
                vendor.setPassword(oldVendor.getPassword());
                vendorService.updateVendor(vendor);
                return "redirect:/vendors";
            } else {
                City vendorCity = cityService.getCityById(vendor.getVendorCity());
                District vendorDistrict = districtService.getDistrictById(vendor.getVendorDistrict());

                model.addAttribute("vendor", vendor);
                model.addAttribute("city", vendorCity);
                model.addAttribute("district", vendorDistrict);
                return "admin/vendorUpdateForm";
            }

        } else {
            vendor.setVendorId(id);
            vendorService.updateVendor(vendor);
            return "redirect:/vendors";
        }
    }


}

