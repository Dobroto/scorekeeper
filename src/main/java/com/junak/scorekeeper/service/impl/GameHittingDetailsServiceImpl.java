package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.GameHittingDetailsRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameHittingDetailsService;
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
            throw new GameNotFoundException("Game hitting details id not found - " + id);
        }

        return theGameHittingDetails;
    }

    @Override
    public GameHittingDetails save(GameHittingDetails gameHittingDetails) {
        return gameHittingDetailsRepository.save(gameHittingDetails);
    }

    @Override
    public void deleteById(int id) {
        gameHittingDetailsRepository.deleteById(id);
    }

    @Override
    public GameHittingDetails getGameHittingDetails(Player player, Game game) {
        return gameHittingDetailsRepository.getGameHittingDetails(player, game);
    }

    @Override
    public List<GameHittingDetails> getGameHittingDetailsOfPlayer(Player player) {
        //TODO
        return null;
    }
}
