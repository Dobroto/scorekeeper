package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.GamePitchingDetailsRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GamePitchingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GamePitchingDetailsServiceImpl implements GamePitchingDetailsService {
    private GamePitchingDetailsRepository gamePitchingDetailsRepository;

    @Autowired
    public GamePitchingDetailsServiceImpl(GamePitchingDetailsRepository gamePitchingDetailsRepository) {
        this.gamePitchingDetailsRepository = gamePitchingDetailsRepository;
    }

    @Override
    public List<GamePitchingDetails> findAll() {
        return gamePitchingDetailsRepository.findAll();
    }

    @Override
    public GamePitchingDetails findById(int id) {
        Optional<GamePitchingDetails> result = gamePitchingDetailsRepository.findById(id);

        GamePitchingDetails theGamePitchingDetails = null;

        if (result.isPresent()) {
            theGamePitchingDetails = result.get();
        } else {
            throw new GameNotFoundException("Game pitching details id not found - " + id);
        }

        return theGamePitchingDetails;
    }

    @Override
    public GamePitchingDetails save(GamePitchingDetails gamePitchingDetails) {
        return gamePitchingDetailsRepository.save(gamePitchingDetails);
    }

    @Override
    public void deleteById(int id) {
        gamePitchingDetailsRepository.deleteById(id);
    }

    @Override
    public GamePitchingDetails getGamePitchingDetails(Player pitcher, Game game) {
        return gamePitchingDetailsRepository.getGamePitchingDetails(pitcher, game);
    }
}
