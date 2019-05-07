package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.Game;

import java.util.List;

public interface GameService {
    List<Game> findAll();

    Game findById(int id);

    void save(Game team);

    void deleteById(int id);

//    void startGame(int gameId);
}
