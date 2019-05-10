package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.PlayerHittingDetails;

import java.util.List;

public interface PlayerHittingDetailsService {
    List<PlayerHittingDetails> findAll();

    PlayerHittingDetails findById(int id);

    void save(PlayerHittingDetails playerHittingDetails);

    void deleteById(int id);
}
