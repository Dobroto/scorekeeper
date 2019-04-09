package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.PlayerFieldingDetails;

import java.util.List;

public interface PlayerFieldingDetailsService {
    List<PlayerFieldingDetails> findAll();

    PlayerFieldingDetails findById(int id);

    void deleteById(int id);
}
