package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.GameHittingDetailsRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
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
    public void save(GameHittingDetails gameHittingDetails) {
        gameHittingDetailsRepository.save(gameHittingDetails);
    }

    @Override
    public void deleteById(int id) {
        gameHittingDetailsRepository.deleteById(id);
    }

    @Override
    public GameHittingDetails getGameHittingDetails(Player batter, Game game) {
        GameHittingDetails theGameHittingDetails = gameHittingDetailsRepository.getGameHittingDetails(batter, game);

        if (theGameHittingDetails == null) {
            throw new GameNotFoundException("Game hitting details not found of player with id " + batter.getId()
                    + "game id " + game.getId());
        }

        return theGameHittingDetails;
    }
}
