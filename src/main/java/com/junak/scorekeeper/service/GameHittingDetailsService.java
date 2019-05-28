package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface GameHittingDetailsService {
    List<GameHittingDetails> findAll();

    GameHittingDetails findById(int id);

    void save(GameHittingDetails gameHittingDetails);

    void deleteById(int id);

    GameHittingDetails getGameHittingDetails(Player batter, Game game);
}
