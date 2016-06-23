package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.entity.Admin;
import com.decimatech.tarim.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(name = "", method = RequestMethod.GET)
    public String getAdminList(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());
        return "admin/adminList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getAdminCreateForm(@ModelAttribute Admin admin) {
        return "admin/adminCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAdmin(@Valid @ModelAttribute Admin admin, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/adminCreateForm";

        } else {
            adminService.registerAdmin(admin);
            return "redirect:/admins";
        }
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getAdminDetails(@PathVariable("id") Long id, Model model) {
        Admin admin = adminService.getAdminByAdminId(id);
        model.addAttribute("admin", admin);
        return "admin/adminUpdateForm";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST)
    public String updateAdmin(@Valid @ModelAttribute Admin admin, BindingResult result, @PathVariable("id") Long id) {

        if (result.hasErrors()) {

            System.out.printf("HATAAAAAAAAAAAAAAA");
            return "redirect:/admins";

        }
        admin.setAdminId(id);
        adminService.updateAdmin(admin);

        return "redirect:/admins";
    }

}
