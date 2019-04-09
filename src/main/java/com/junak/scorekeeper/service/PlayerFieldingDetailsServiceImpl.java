package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.PlayerFieldingDetailsRepository;
import com.junak.scorekeeper.entity.PlayerFieldingDetails;
import com.junak.scorekeeper.rest.error.player_fielding_details_error.PlayerFieldingDetailsNotFoundException;
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
            throw new PlayerFieldingDetailsNotFoundException("Player fielding details id not found - " + id);
        }

        return thePlayerFieldingDetails;
    }

    @Override
    public void deleteById(int id) {

    }
}
