package com.decimatech.tarim.service;

import com.decimatech.tarim.model.ReplacedPart;
import com.decimatech.tarim.repository.ReplacedPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.decimatech.tarim.controller.MaintainController.REPLACED_PART_COUNT;


@Service
@Transactional
public class ReplacedPartServiceImpl implements ReplacedPartService {

    @Autowired
    private ReplacedPartRepository replacedPartRepository;

    @Override
    public void createAllReplacedParts(Long maintainId) {
        for (int i = 0; i<REPLACED_PART_COUNT; i++){
            ReplacedPart replacedPart = new ReplacedPart();
            replacedPart.setMaintainId(maintainId);
            replacedPartRepository.save(replacedPart);
        }
    }

    @Override
    public List<ReplacedPart> updateAllReplacedParts(List<ReplacedPart> replacedPartList, Long maintainId) {

        List<ReplacedPart> oldReplacedPartList = replacedPartRepository.findByMaintainIdOrderByReplacedPartIdAsc(maintainId);

        for(int i = 0; i<REPLACED_PART_COUNT; i++){
            replacedPartList.get(i).setReplacedPartId(oldReplacedPartList.get(i).getReplacedPartId());
            replacedPartList.get(i).setMaintainId(oldReplacedPartList.get(i).getMaintainId());
            replacedPartRepository.save(replacedPartList.get(i));
        }

        return replacedPartList;
    }
}
