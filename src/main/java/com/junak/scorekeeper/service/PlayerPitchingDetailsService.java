package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.PlayerPitchingDetails;

import java.util.List;

public interface PlayerPitchingDetailsService {
    List<PlayerPitchingDetails> findAll();

    PlayerPitchingDetails findById(int id);

    void deleteById(int id);
}
