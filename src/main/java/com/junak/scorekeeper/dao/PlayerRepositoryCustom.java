package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import java.util.List;

public interface PlayerRepositoryCustom {

    List<Player> findAllTeamPlayers(int teamId);

    //public void assignPlayerToTeam();
}
