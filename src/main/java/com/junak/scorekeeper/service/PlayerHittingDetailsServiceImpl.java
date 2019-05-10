package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.PlayerHittingDetailsRepository;
import com.junak.scorekeeper.entity.PlayerHittingDetails;
import com.junak.scorekeeper.rest.error.player_hitting_details_error.PlayerHittingDetailsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerHittingDetailsServiceImpl implements PlayerHittingDetailsService {
    private PlayerHittingDetailsRepository playerHittingDetailsRepository;

    @Autowired
    public PlayerHittingDetailsServiceImpl(PlayerHittingDetailsRepository playerHittingDetailsRepository) {
        this.playerHittingDetailsRepository = playerHittingDetailsRepository;
    }

    @Override
    public List<PlayerHittingDetails> findAll() {
        return playerHittingDetailsRepository.findAll();
    }

    @Override
    public PlayerHittingDetails findById(int id) {
        Optional<PlayerHittingDetails> result = playerHittingDetailsRepository.findById(id);

        PlayerHittingDetails thePlayerHittingDetails = null;

        if (result.isPresent()) {
            thePlayerHittingDetails = result.get();
        } else {
            throw new PlayerHittingDetailsNotFoundException("Player hitting details id not found - " + id);
        }

        return thePlayerHittingDetails;
    }

    @Override
    public void save(PlayerHittingDetails playerHittingDetails) {
        playerHittingDetailsRepository.save(playerHittingDetails);
    }

    @Override
    public void deleteById(int id) {
        playerHittingDetailsRepository.deleteById(id);
    }

}
