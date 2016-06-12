package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.Demand;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DemandRepository extends Repository<Demand, Long> {

    List<Demand> findAll();

    List<Demand> findByVendorIdAndDeletedFalse(Long vendorId);

    List<Demand> findByVendorIdAndDeletedFalseAndUnreadTrue(Long vendorId);

    Demand save(Demand demand);

    void delete(Demand demand);

    Demand findOne(Long id);

    void flush();
}
