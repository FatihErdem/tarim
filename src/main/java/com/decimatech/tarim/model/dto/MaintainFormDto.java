package com.decimatech.tarim.model.dto;

import com.decimatech.tarim.model.Demand;
import com.decimatech.tarim.model.Maintain;
import com.decimatech.tarim.model.ReplacedPart;

import javax.validation.Valid;
import java.util.List;

public class MaintainFormDto {

    @Valid
    private Maintain maintain;

    @Valid
    private Demand demand;

    private List<ReplacedPart> replacedPartList;

    public MaintainFormDto() {
    }

    public MaintainFormDto(Maintain maintain, Demand demand, List<ReplacedPart> replacedPartList) {
        this.maintain = maintain;
        this.demand = demand;
        this.replacedPartList = replacedPartList;
    }

    public Maintain getMaintain() {
        return maintain;
    }

    public void setMaintain(Maintain maintain) {
        this.maintain = maintain;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public List<ReplacedPart> getReplacedPartList() {
        return replacedPartList;
    }

    public void setReplacedPartList(List<ReplacedPart> replacedPartList) {
        this.replacedPartList = replacedPartList;
    }
}
