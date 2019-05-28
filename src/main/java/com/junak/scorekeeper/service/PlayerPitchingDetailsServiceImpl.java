package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.PlayerPitchingDetailsRepository;
import com.junak.scorekeeper.entity.PlayerPitchingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerPitchingDetailsServiceImpl implements PlayerPitchingDetailsService {
    private PlayerPitchingDetailsRepository playerPitchingDetailsRepository;

    @Autowired
    public PlayerPitchingDetailsServiceImpl(PlayerPitchingDetailsRepository playerPitchingDetailsRepository) {
        this.playerPitchingDetailsRepository = playerPitchingDetailsRepository;
    }

    @Override
    public List<PlayerPitchingDetails> findAll() {
        return playerPitchingDetailsRepository.findAll();
    }

    @Override
    public PlayerPitchingDetails findById(int id) {
        Optional<PlayerPitchingDetails> result = playerPitchingDetailsRepository.findById(id);

        PlayerPitchingDetails thePlayerPitchingDetails = null;

        if (result.isPresent()) {
            thePlayerPitchingDetails = result.get();
        } else {
            throw new GameNotFoundException("Player pitching details id not found - " + id);
        }

        return thePlayerPitchingDetails;
    }

    @Override
    public void save(PlayerPitchingDetails playerPitchingDetails) {
        playerPitchingDetailsRepository.save(playerPitchingDetails);
    }

    @Override
    public void deleteById(int id) {
        playerPitchingDetailsRepository.deleteById(id);
    }
}
