package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.District;

import java.util.List;

public interface DistrictService  {

    List<District> getDistrictsByCityId(Long id);

    District getDistrictById(Long id);
}
