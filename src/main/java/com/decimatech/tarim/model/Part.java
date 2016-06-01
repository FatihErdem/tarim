package com.decimatech.tarim.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "part_id")
    private Long partId;

    @NotBlank
    @Column(name = "part_name")
    private String partName;

    private Long machineName;

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Long getMachineName() {
        return machineName;
    }

    public void setMachineName(Long machineName) {
        this.machineName = machineName;
    }
}
