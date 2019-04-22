package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.GameFieldingDetails;

import java.util.List;

public interface GameFieldingDetailsService {
    List<GameFieldingDetails> findAll();

    GameFieldingDetails findById(int id);

    void deleteById(int id);
}