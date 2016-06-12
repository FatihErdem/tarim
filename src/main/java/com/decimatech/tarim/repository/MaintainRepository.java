package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.Maintain;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MaintainRepository extends Repository<Maintain, Long> {

    Maintain save(Maintain maintain);

    List<Maintain> findAll();

    List<Maintain> findByVendorId(Long id);

    Maintain findOne(Long id);

}
