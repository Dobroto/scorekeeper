package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.PlayerPitchingDetails;

import java.util.List;

public interface PlayerPitchingDetailsService {
    List<PlayerPitchingDetails> findAll();

    PlayerPitchingDetails findById(int id);

    PlayerPitchingDetails save(PlayerPitchingDetails playerPitchingDetails);

    void deleteById(int id);
}
