package com.decimatech.tarim.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "machine_id")
    private Long machineId;

    @NotNull
    @Column(name = "machine_type")
    private Long machineType;

    @NotBlank
    @Column(name = "machine_name")
    private String machineName;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getMachineType() {
        return machineType;
    }

    public void setMachineType(Long machineType) {
        this.machineType = machineType;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    @Override
    public String toString() {
        return machineName;
    }
}
