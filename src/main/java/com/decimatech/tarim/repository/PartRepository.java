package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.Part;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PartRepository extends Repository<Part, Long> {

    Part findOne(Long id);

    List<Part> findAll();

    Part save(Part part);

}
