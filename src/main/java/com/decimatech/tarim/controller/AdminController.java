package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.entity.Admin;
import com.decimatech.tarim.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getAdminCreateForm(@ModelAttribute Admin admin){
        return "adminCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAdmin(@Valid @ModelAttribute Admin admin, BindingResult result){
        if (result.hasErrors()) {
            return "admin/adminCreateForm";

        } else {
            adminService.registerAdmin(admin);
            return "redirect:/";
        }
    }


}
