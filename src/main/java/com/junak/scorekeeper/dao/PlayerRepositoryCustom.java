package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface PlayerRepositoryCustom {

    List<Player> findAllTeamPlayers(int teamId);

    Player getFirstBaseRunner(Team team);

    Player getSecondBaseRunner(Team team);

    Player getThirdBaseRunner(Team team);

    Player getStartingBatter(Team team);

    Player getNextBatter(Player currentBatter);

    Player getPitcher(Team team);
}
