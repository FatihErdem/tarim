package com.decimatech.tarim.repository;

import com.decimatech.tarim.model.entity.Image;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ImageRepository extends Repository<Image, Long> {

    void save(Image image);

    List<Image> findByMaintainId(Long maintainId);
}
