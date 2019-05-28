package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface GameFieldingDetailsService {
    List<GameFieldingDetails> findAll();

    GameFieldingDetails findById(int id);

    void save(GameFieldingDetails gameFieldingDetails);

    void deleteById(int id);

    GameFieldingDetails getGameFieldingDetails(Player fielder, Game game);
}
