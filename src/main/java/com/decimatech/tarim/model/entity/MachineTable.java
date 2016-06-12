package com.decimatech.tarim.model.entity;


import com.decimatech.tarim.model.entity.Machine;

public class MachineTable extends Machine {

    private String machineTypeName;

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }
}
