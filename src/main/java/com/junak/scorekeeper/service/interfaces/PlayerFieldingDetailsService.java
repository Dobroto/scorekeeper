package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.PlayerFieldingDetails;

import java.util.List;

public interface PlayerFieldingDetailsService {
    List<PlayerFieldingDetails> findAll();

    PlayerFieldingDetails findById(int id);

    PlayerFieldingDetails save(PlayerFieldingDetails playerFieldingDetails);

    void deleteById(int id);
}
