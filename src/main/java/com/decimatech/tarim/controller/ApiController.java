package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.domain.Notification;
import com.decimatech.tarim.model.domain.NotificationDetail;
import com.decimatech.tarim.model.entity.City;
import com.decimatech.tarim.model.entity.Demand;
import com.decimatech.tarim.model.entity.District;
import com.decimatech.tarim.model.entity.Maintain;
import com.decimatech.tarim.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private CityService cityService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private DemandService demandService;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private MaintainService maintainService;

    @ResponseBody
    @RequestMapping(value = "/notifications", method = RequestMethod.GET, headers = "Accept=application/json")
    public Notification getNotification(Authentication authentication) {

        //TODO gelen talep turune gore ve demandStateine gore notification olustur

        List<Demand> demands = demandService.gellAllUnreadDemands(authentication);

        List<NotificationDetail> notifications = new ArrayList<>();

        Notification notification = new Notification();
        for (Demand demand : demands) {
            NotificationDetail notificationDetail = new NotificationDetail();
            if (Objects.equals(demand.getDemandState(), "OPEN")) {
                String date = new SimpleDateFormat("dd/MM HH:mm").format(demand.getCreationDate().getTime());
                notificationDetail.setDate(date);
                notificationDetail.setDemandOrMaintainId(demand.getDemandId());
                notificationDetail.setState(demand.getDemandState());
            }else{
                String date = new SimpleDateFormat("dd/MM HH:mm").format(demand.getUpdateDate().getTime());
                notificationDetail.setDate(date);
                Maintain maintain = maintainService.getMaintainByDemandId(demand.getDemandId());
                notificationDetail.setDemandOrMaintainId(maintain.getMaintainId());
                notificationDetail.setState(demand.getDemandState());
            }

            notifications.add(notificationDetail);
        }

        notification.setDetails(notifications);
        notification.setUnreadCount(notifications.size());

        return notification;

    }

    @ResponseBody
    @RequestMapping(value = "/cities", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<City> getCities() {
        return cityService.getAllCities();
    }

    @ResponseBody
    @RequestMapping(value = "/districts/{cityId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<District> getDistricts(@PathVariable("cityId") Long cityId) {

        return districtService.getDistrictsByCityId(cityId);
    }

}
