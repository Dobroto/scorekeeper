package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.GameFieldingDetailsRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
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
            throw new GameNotFoundException("Game fielding details id not found - " + id);
        }

        return theGameFieldingDetails;
    }

    @Override
    public void save(GameFieldingDetails gameFieldingDetails) {
        gameFieldingDetailsRepository.save(gameFieldingDetails);
    }

    @Override
    public void deleteById(int id) {
        gameFieldingDetailsRepository.deleteById(id);
    }

    @Override
    public GameFieldingDetails getGameFieldingDetails(Player fielder, Game game) {
        GameFieldingDetails theGameFieldingDetails = gameFieldingDetailsRepository.getGameFieldingDetails(fielder, game);

        if (theGameFieldingDetails == null) {
            throw new GameNotFoundException("Game fielding details not found of player with id " + fielder.getId()
                    + "game id " + game.getId());
        }

        return theGameFieldingDetails;
    }
}
