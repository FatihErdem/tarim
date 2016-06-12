package com.decimatech.tarim.service;


import com.decimatech.tarim.model.entity.City;

import java.util.List;

public interface CityService {

    List<City> getAllCities();

    City getCityById(Long id);

}
