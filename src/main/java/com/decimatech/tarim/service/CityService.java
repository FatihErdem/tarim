package com.decimatech.tarim.service;


import com.decimatech.tarim.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CityService {

    List<City> getAllCities();

    City getCityById(Long id);

}
