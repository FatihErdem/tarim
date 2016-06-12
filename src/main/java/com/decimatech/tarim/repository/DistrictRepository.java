package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.District;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DistrictRepository extends Repository<District, Long> {

    @Cacheable("districts")
    List<District> findByCityIdOrderByDistrictNameAsc(Long id);

    @Cacheable("district")
    District findByDistrictId(Long id);
}
