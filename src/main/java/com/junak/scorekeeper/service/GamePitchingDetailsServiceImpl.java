package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.GamePitchingDetailsRepository;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.rest.error.game_pitching_details_error.GamePitchingDetailsNotFoundException;
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
            throw new GamePitchingDetailsNotFoundException("Game pitching details id not found - " + id);
        }

        return theGamePitchingDetails;
    }

    @Override
    public void save(GamePitchingDetails gamePitchingDetails) {
        gamePitchingDetailsRepository.save(gamePitchingDetails);
    }

    @Override
    public void deleteById(int id) {
        gamePitchingDetailsRepository.deleteById(id);
    }
}
