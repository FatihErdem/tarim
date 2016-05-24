package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.Demand;
import com.decimatech.tarim.model.Notification;
import com.decimatech.tarim.model.NotificationDetail;
import com.decimatech.tarim.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private DemandService demandService;

    @RequestMapping(value = "/notifications", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Notification getNotification(Authentication authentication) {

        Notification notification = new Notification();
        List<Demand> demands = demandService.gellAllUnreadDemands(authentication);
        Date now = new Date();

        List<NotificationDetail> notificationDetails = new ArrayList<>();

        for (int i = 0; i < demands.size(); i++) {
            NotificationDetail notificationDetail = new NotificationDetail();



            notificationDetail.setDemandId(Math.toIntExact(demands.get(i).getDemandId()));
            notificationDetail.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(demands.get(i).getCreationDate().getTime())));
            notificationDetails.add(notificationDetail);
        }

        notification.setDetails(notificationDetails);
        notification.setUnreadCount(demands.size());

        return notification;

    }
}
