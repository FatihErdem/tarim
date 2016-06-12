package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.*;
import com.decimatech.tarim.repository.CityRepository;
import com.decimatech.tarim.repository.DistrictRepository;
import com.decimatech.tarim.service.CityService;
import com.decimatech.tarim.service.DemandService;
import com.decimatech.tarim.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private DemandService demandService;

    @ResponseBody
    @RequestMapping(value = "/notifications", method = RequestMethod.GET, headers = "Accept=application/json")
    public Notification getNotification(Authentication authentication) {

        Notification notification = new Notification();
        List<Demand> demands = demandService.gellAllUnreadDemands(authentication);

        List<NotificationDetail> notificationDetails = new ArrayList<>();

        for (int i = 0; i < demands.size(); i++) {
            NotificationDetail notificationDetail = new NotificationDetail();


            notificationDetail.setDemandId(Math.toIntExact(demands.get(i).getDemandId()));
            notificationDetail.setCreatedAt(new SimpleDateFormat("dd/MM HH:mm").format(new Date(demands.get(i).getCreationDate().getTime())));
            notificationDetails.add(notificationDetail);
        }

        notification.setDetails(notificationDetails);
        notification.setUnreadCount(demands.size());

        return notification;

    }

    @ResponseBody
    @RequestMapping(value = "/cities", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<City> getCities() {
        return cityService.getAllCities();
    }

    @ResponseBody
    @RequestMapping(value = "/districts/{cityId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<District> getDistricts(@PathVariable("cityId") Long cityId){

        return districtService.getDistrictsByCityId(cityId);
    }

}
