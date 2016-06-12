package com.decimatech.tarim.model.entity;

import com.decimatech.tarim.model.entity.AbstractBaseModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Part  extends AbstractBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "part_id")
    private Long partId;

    @NotBlank
    @Column(name = "part_name")
    private String partName;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "price")
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

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

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}
