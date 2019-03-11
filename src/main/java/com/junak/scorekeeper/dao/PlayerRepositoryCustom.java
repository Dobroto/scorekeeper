package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface PlayerRepositoryCustom {

    public List<Player> findAllTeamPlayers(int teamId);

    //public void assignPlayerToTeam();
}
