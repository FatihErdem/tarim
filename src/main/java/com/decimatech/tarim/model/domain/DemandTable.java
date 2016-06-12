package com.decimatech.tarim.model.domain;

import com.decimatech.tarim.model.entity.Demand;

public class DemandTable extends Demand {

    private String vendorName;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
