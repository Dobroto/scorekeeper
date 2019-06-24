package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.InningRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.InningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InningServiceImpl implements InningService {
    private InningRepository inningRepository;

    @Autowired
    public InningServiceImpl (InningRepository inningRepository) {
        this.inningRepository = inningRepository;
    }

    @Override
    public List<Inning> findAll() {
        return inningRepository.findAll();
    }

    @Override
    public Inning findById(int id) {
        Optional<Inning> result = inningRepository.findById(id);

        Inning theInning = null;

        if (result.isPresent()) {
            theInning = result.get();
        } else {
            throw new GameNotFoundException("Inning id not found - " + id);
        }

        return theInning;
    }

    @Override
    public Inning save(Inning inning) {
        return inningRepository.save(inning);
    }

    @Override
    public void deleteById(int id) {
        inningRepository.deleteById(id);
    }

    @Override
    public Inning getCurrentInning(Game game) {
        return inningRepository.getCurrentInning(game);
    }
}
