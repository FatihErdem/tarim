package com.decimatech.tarim.model.domain;

public class NotificationDetail {

    private Long demandOrMaintainId;
    private String state;
    private String date;

    public Long getDemandOrMaintainId() {
        return demandOrMaintainId;
    }

    public void setDemandOrMaintainId(Long demandOrMaintainId) {
        this.demandOrMaintainId = demandOrMaintainId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
