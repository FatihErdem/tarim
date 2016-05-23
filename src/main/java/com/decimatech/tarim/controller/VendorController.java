package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.Vendor;
import com.decimatech.tarim.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import java.util.List;

@Controller
@RequestMapping(value = "/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getVendorCreateForm(@ModelAttribute Vendor vendor) {
        return "vendorForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createVendor(@Valid @ModelAttribute Vendor vendor, BindingResult result) {

        if (result.hasErrors()) {
            return "vendorForm";

        } else {
            vendorService.registerVendor(vendor);
            return "redirect:/demands";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getVendorList(Model model){
        List<Vendor> vendors = vendorService.getAllVendors();

        model.addAttribute("vendors", vendors);
        return "vendorList";
    }

}

