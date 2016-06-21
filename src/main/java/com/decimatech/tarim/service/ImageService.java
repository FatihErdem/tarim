package com.decimatech.tarim.service;

import com.decimatech.tarim.model.entity.Image;

import java.util.List;

public interface ImageService {

    void saveImagesForMaintain(Long maintainId, String imageUrl);

    List<Image> getByMaintainId(Long maintainId);

}
