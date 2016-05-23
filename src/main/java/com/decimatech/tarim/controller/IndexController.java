package com.decimatech.tarim.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping(value = "/")
public class IndexController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getIndexPage(Model model, Authentication authentication, Principal principal) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isVendor = authorities.contains(new SimpleGrantedAuthority("VENDOR"));
        model.addAttribute("role", authentication.getName());
        return "index";
    }
}
