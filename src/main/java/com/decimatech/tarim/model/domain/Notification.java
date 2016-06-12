package com.decimatech.tarim.model.domain;


import java.util.List;

public class Notification {

    private Integer unreadCount;
    private List<NotificationDetail> details;

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public List<NotificationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<NotificationDetail> details) {
        this.details = details;
    }
}
