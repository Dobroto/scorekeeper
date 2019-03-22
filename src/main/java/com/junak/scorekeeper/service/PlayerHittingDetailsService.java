package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.PlayerHittingDetails;

import java.util.List;

public interface PlayerHittingDetailsService {
    List<PlayerHittingDetails> findAll();

    PlayerHittingDetails findById(int id);

    void save(PlayerHittingDetails team);

    void deleteById(int id);

    PlayerHittingDetails getPlayerHittingDetails(int playerId);
}
