package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.PlayRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import com.junak.scorekeeper.entity.Play;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayServiceImpl implements PlayService {
    private PlayRepository playRepository;

    @Autowired
    public PlayServiceImpl(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    @Override
    public Play findById(int id) {
        Optional<Play> result = playRepository.findById(id);

        Play thePlay = null;

        if (result.isPresent()) {
            thePlay = result.get();
        } else {
            throw new GameNotFoundException("Play id not found - " + id);
        }

        return thePlay;
    }

    @Override
    public Play save(Play play) {
        return playRepository.save(play);
    }

    @Override
    public void deleteById(int id) {
        playRepository.deleteById(id);
    }

    @Override
    public List<Play> getAllPlaysInInning(Inning inning) {
        return playRepository.getAllPlaysInInning(inning);
    }

    @Override
    public List<Play> getAllPlaysInGame(Game game) {
        return playRepository.getAllPlaysInGame(game);
    }
}
