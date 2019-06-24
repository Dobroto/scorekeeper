package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.FinalResultRepository;
import com.junak.scorekeeper.entity.FinalResult;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.FinalResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinalResultServiceImpl implements FinalResultService {
    private FinalResultRepository finalResultRepository;

    public FinalResultServiceImpl(FinalResultRepository finalResultRepository) {
        this.finalResultRepository = finalResultRepository;
    }

    @Override
    public List<FinalResult> findAll() {
        return finalResultRepository.findAll();
    }

    @Override
    public FinalResult findById(int id) {
        Optional<FinalResult> result = finalResultRepository.findById(id);

        FinalResult theFinalResult = null;

        if (result.isPresent()) {
            theFinalResult = result.get();
        } else {
            throw new GameNotFoundException("Final result id not found - " + id);
        }

        return theFinalResult;
    }

    @Override
    public FinalResult save(FinalResult finalResult) {
        return finalResultRepository.save(finalResult);
    }

    @Override
    public void deleteById(int id) {
        finalResultRepository.deleteById(id);
    }
}
