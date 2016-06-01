package com.decimatech.tarim.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class MachineType {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "machine_type_id")
    private Long machineTypeId;

    @NotBlank
    @Column(name = "machine_type_name")
    private String machineTypeName;

    public Long getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(Long machineTypeId) {
        this.machineTypeId = machineTypeId;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    @Override
    public String toString() {
        return machineTypeName;
    }
}
