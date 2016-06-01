package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.Machine;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MachineRepository extends Repository<Machine, Long> {

    Machine save(Machine machine);

    List<Machine> findAll();

    Machine findOne(Long id);

}
