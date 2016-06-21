package com.decimatech.tarim.service;


import com.decimatech.tarim.model.entity.Image;
import com.decimatech.tarim.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void saveImagesForMaintain(Long maintainId, String imageUrl) {
        Image image = new Image(maintainId, imageUrl);
        imageRepository.save(image);
    }

    @Override
    public List<Image> getByMaintainId(Long maintainId) {
        return imageRepository.findByMaintainId(maintainId);
    }
}
