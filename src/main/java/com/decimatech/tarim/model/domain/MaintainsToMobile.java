package com.decimatech.tarim.model.domain;

import com.decimatech.tarim.model.dto.DemandJsonDto;

import java.util.List;

public class MaintainsToMobile {

    private List<DemandJsonDto> maintains;

    public List<DemandJsonDto> getMaintains() {
        return maintains;
    }

    public void setMaintains(List<DemandJsonDto> maintains) {
        this.maintains = maintains;
    }
}
