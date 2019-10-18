package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Game;

import java.util.List;

public interface GameService {
    List<Game> findAll();

    Game findById(int id);

    Game save(Game team);

    void deleteById(int id);

//    void startGame(int gameId);
}
