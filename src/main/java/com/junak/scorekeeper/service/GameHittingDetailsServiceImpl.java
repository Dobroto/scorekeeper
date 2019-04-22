package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.GameHittingDetailsRepository;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.rest.error.game_hitting_details_error.GameHittingDetailsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameHittingDetailsServiceImpl implements GameHittingDetailsService {
    private GameHittingDetailsRepository gameHittingDetailsRepository;

    @Autowired
    public GameHittingDetailsServiceImpl(GameHittingDetailsRepository gameHittingDetailsRepository) {
        this.gameHittingDetailsRepository = gameHittingDetailsRepository;
    }

    @Override
    public List<GameHittingDetails> findAll() {
        return gameHittingDetailsRepository.findAll();
    }

    @Override
    public GameHittingDetails findById(int id) {
        Optional<GameHittingDetails> result = gameHittingDetailsRepository.findById(id);

        GameHittingDetails theGameHittingDetails = null;

        if (result.isPresent()) {
            theGameHittingDetails = result.get();
        } else {
            throw new GameHittingDetailsNotFoundException("Game hitting details id not found - " + id);
        }

        return theGameHittingDetails;
    }

    @Override
    public void deleteById(int id) {
        gameHittingDetailsRepository.deleteById(id);
    }
}
