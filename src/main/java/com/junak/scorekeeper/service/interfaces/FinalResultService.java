package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.FinalResult;

import java.util.List;

public interface FinalResultService {

    List<FinalResult> findAll();

    FinalResult findById(int id);

    FinalResult save(FinalResult finalResult);

    void deleteById(int id);
}
