package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.City;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CityRepository extends Repository<City, Long> {

    List<City> findAllByOrderByCityNameAsc();
    City findByCityId(Long id);
}
