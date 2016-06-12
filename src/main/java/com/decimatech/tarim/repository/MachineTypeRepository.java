package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.MachineType;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MachineTypeRepository extends Repository<MachineType, Long> {

    MachineType save(MachineType machineType);

    List<MachineType> findAll();

    MachineType findOne(Long id);

}
