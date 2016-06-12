package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.City;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CityRepository extends Repository<City, Long> {

    @Cacheable("cities")
    List<City> findAllByOrderByCityNameAsc();

    @Cacheable("city")
    City findByCityId(Long id);
}
