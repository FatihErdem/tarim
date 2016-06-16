package com.decimatech.tarim.config;

import com.decimatech.tarim.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AdminService adminService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("=====================================================================================");

        if(!adminService.ifFirstAdminExist()){
            adminService.createFirstAdmin();
        }

    }
}