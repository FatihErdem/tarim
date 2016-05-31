package com.decimatech.tarim.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/maintain")
public class MaintainController {

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateForm(){

        return "maintainForm";
    }

}
