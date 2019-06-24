package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.PlayerFieldingDetailsRepository;
import com.junak.scorekeeper.entity.PlayerFieldingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.PlayerFieldingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerFieldingDetailsServiceImpl implements PlayerFieldingDetailsService {
    private PlayerFieldingDetailsRepository playerFieldingDetailsRepository;

    @Autowired
    public PlayerFieldingDetailsServiceImpl(PlayerFieldingDetailsRepository playerFieldingDetailsRepository) {
        this.playerFieldingDetailsRepository = playerFieldingDetailsRepository;
    }

    @Override
    public List<PlayerFieldingDetails> findAll() {
        return playerFieldingDetailsRepository.findAll();
    }

    @Override
    public PlayerFieldingDetails findById(int id) {
        Optional<PlayerFieldingDetails> result = playerFieldingDetailsRepository.findById(id);

        PlayerFieldingDetails thePlayerFieldingDetails = null;

        if (result.isPresent()) {
            thePlayerFieldingDetails = result.get();
        } else {
            throw new GameNotFoundException("Player fielding details id not found - " + id);
        }

        return thePlayerFieldingDetails;
    }

    @Override
    public PlayerFieldingDetails save(PlayerFieldingDetails playerFieldingDetails) {
        return playerFieldingDetailsRepository.save(playerFieldingDetails);
    }

    @Override
    public void deleteById(int id) {
        playerFieldingDetailsRepository.deleteById(id);
    }
}
