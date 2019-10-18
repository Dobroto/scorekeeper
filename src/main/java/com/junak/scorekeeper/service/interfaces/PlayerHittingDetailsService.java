package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.PlayerHittingDetails;

import java.util.List;

public interface PlayerHittingDetailsService {
    List<PlayerHittingDetails> findAll();

    PlayerHittingDetails findById(int id);

    PlayerHittingDetails save(PlayerHittingDetails playerHittingDetails);

    void deleteById(int id);
}
