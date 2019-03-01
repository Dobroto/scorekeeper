package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface PlayerService {
    public List<Player> findAll();

    public List<Player> findAllTeamPlayers(int teamId);

    public Player findById(int id);

    public void save(Player player);

    public void deleteById(int id);
}
