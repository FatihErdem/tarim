package com.decimatech.tarim.controller;


import com.decimatech.tarim.service.DemandService;
import com.decimatech.tarim.service.MaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private DemandService demandService;

    @Autowired
    private MaintainService maintainService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getIndexPage(Authentication authentication, Model model) {

        model.addAttribute("totalDemandCount", demandService.getDemandCount(authentication));
        model.addAttribute("totalInProgressDemandCount", demandService.getInProgresDemandCount(authentication));
        model.addAttribute("totalApprovedDemandCount", demandService.getApprovedDemandCount(authentication));
        return "index";
    }
}
