package com.decimatech.tarim.model;

import javax.persistence.*;

@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "district_name")
    private String districtName;


    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return districtName;
    }
}
