package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.PlayerHittingDetails;

import java.util.List;

public interface PlayerHittingDetailsService {
    List<PlayerHittingDetails> findAll();

    PlayerHittingDetails findById(int id);

    void deleteById(int id);
}
