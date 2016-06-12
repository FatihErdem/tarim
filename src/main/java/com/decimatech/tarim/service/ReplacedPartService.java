package com.decimatech.tarim.service;

import com.decimatech.tarim.model.ReplacedPart;

import java.util.List;

public interface ReplacedPartService {


    void createAllReplacedParts(Long maintainId);

    List<ReplacedPart> updateAllReplacedParts(List<ReplacedPart> replacedPartList, Long maintainId);
}
