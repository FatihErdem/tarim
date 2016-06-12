package com.decimatech.tarim.service;

import com.decimatech.tarim.model.City;
import com.decimatech.tarim.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAllByOrderByCityNameAsc();
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findByCityId(id);
    }
}
