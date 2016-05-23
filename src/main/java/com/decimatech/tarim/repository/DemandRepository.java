package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.Demand;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DemandRepository extends Repository<Demand, Long>, QueryDslPredicateExecutor<Demand> {

    List<Demand> findAll();

    List<Demand> findByVendorIdAndDeletedFalse(Long vendorId);

    Demand save(Demand demand);

    void delete(Demand demand);

    Demand findOne(Long id);

    void flush();
}
