package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.District;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DistrictRepository extends Repository<District, Long> {

    List<District> findByCityIdOrderByDistrictNameAsc(Long id);

    District findByDistrictId(Long id);
}
