package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.GamePitchingDetails;

import java.util.List;

public interface GamePitchingDetailsService {
    List<GamePitchingDetails> findAll();

    GamePitchingDetails findById(int id);

    void deleteById(int id);
}
