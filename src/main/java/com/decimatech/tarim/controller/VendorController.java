package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.City;
import com.decimatech.tarim.model.District;
import com.decimatech.tarim.model.Vendor;
import com.decimatech.tarim.repository.CityRepository;
import com.decimatech.tarim.repository.DistrictRepository;
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
    private VendorService vendorService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getVendorCreateForm(@ModelAttribute Vendor vendor) {
        return "vendorCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createVendor(@Valid @ModelAttribute Vendor vendor, BindingResult result, Model model) {

        if (result.hasErrors()) {

            City vendorCity = cityRepository.findByCityId(vendor.getVendorCity());
            District vendorDistrict = districtRepository.findByDistrictId(vendor.getVendorDistrict());

            model.addAttribute("vendor", vendor);
            model.addAttribute("city", vendorCity);
            model.addAttribute("district", vendorDistrict);
            return "vendorCreateForm";

        } else {
            vendorService.registerVendor(vendor);
            return "redirect:/vendors";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getVendorList(Model model) {
        List<Vendor> vendors = vendorService.getAllVendors();

        model.addAttribute("vendors", vendors);
        return "vendorList";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getVendorDetails(@PathVariable("id") Long id, Model model) {

        Vendor vendor = vendorService.getVendorByVendorId(id);

        City vendorCity = cityRepository.findByCityId(vendor.getVendorCity());
        District vendorDistrict = districtRepository.findByDistrictId(vendor.getVendorDistrict());

        model.addAttribute("vendor", vendor);
        model.addAttribute("city", vendorCity);
        model.addAttribute("district", vendorDistrict);
        return "vendorUpdateForm";
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
                City vendorCity = cityRepository.findByCityId(vendor.getVendorCity());
                District vendorDistrict = districtRepository.findByDistrictId(vendor.getVendorDistrict());

                model.addAttribute("vendor", vendor);
                model.addAttribute("city", vendorCity);
                model.addAttribute("district", vendorDistrict);
                return "vendorUpdateForm";
            }

        } else {
            vendor.setVendorId(id);
            vendorService.updateVendor(vendor);
            return "redirect:/vendors";
        }
    }


}

