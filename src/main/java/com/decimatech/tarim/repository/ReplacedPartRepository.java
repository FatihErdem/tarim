package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.ReplacedPart;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReplacedPartRepository extends Repository<ReplacedPart, Long> {

    ReplacedPart save(ReplacedPart replacedPart);

    List<ReplacedPart> findAll();

    List<ReplacedPart> findByMaintainId(Long id);
}
