package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface PlayerDAO {

    public List<Player> findAll();

    public List<Player> findAllTeamPlayers(int teamId);

    public Player findById(int id);

    public void save(Player player);

    public void deleteById(int id);

    //public void assignPlayerToTeam();
}
