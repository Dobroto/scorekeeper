package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.GameFieldingDetailsRepository;
import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.rest.error.game_fielding_details_error.GameFieldingDetailsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameFieldingDetailsServiceImpl implements GameFieldingDetailsService {
    private GameFieldingDetailsRepository gameFieldingDetailsRepository;

    @Autowired
    public GameFieldingDetailsServiceImpl(GameFieldingDetailsRepository gameFieldingDetailsRepository) {
        this.gameFieldingDetailsRepository = gameFieldingDetailsRepository;
    }

    @Override
    public List<GameFieldingDetails> findAll() {
        return gameFieldingDetailsRepository.findAll();
    }

    @Override
    public GameFieldingDetails findById(int id) {
        Optional<GameFieldingDetails> result = gameFieldingDetailsRepository.findById(id);

        GameFieldingDetails theGameFieldingDetails = null;

        if (result.isPresent()) {
            theGameFieldingDetails = result.get();
        } else {
            throw new GameFieldingDetailsNotFoundException("Game fielding details id not found - " + id);
        }

        return theGameFieldingDetails;
    }

    @Override
    public void deleteById(int id) {
        gameFieldingDetailsRepository.deleteById(id);
    }
}
