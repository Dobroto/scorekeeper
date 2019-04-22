package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> findAll();

    List<Player> findAllTeamPlayers(int teamId);

    Player findById(int id);

    void save(Player player);

    void deleteById(int id);
}
