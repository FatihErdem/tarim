package com.decimatech.tarim.service;

import com.decimatech.tarim.model.District;
import com.decimatech.tarim.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("districtService")
@Transactional
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<District> getDistrictsByCityId(Long id) {
        return districtRepository.findByCityIdOrderByDistrictNameAsc(id);
    }

    @Override
    public District getDistrictById(Long id) {
        return districtRepository.findByDistrictId(id);
    }
}
