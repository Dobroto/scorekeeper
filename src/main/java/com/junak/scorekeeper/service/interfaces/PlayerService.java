package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface PlayerService {
    List<Player> findAll();

    List<Player> findAllTeamPlayers(int teamId);

    Player findById(int id);

    Player save(Player player);

    void deleteById(int id);

    Player getFirstBaseRunner(Team team);

    Player getSecondBaseRunner(Team team);

    Player getThirdBaseRunner(Team team);

    Player getStartingBatter(Team team);

    Player getNextBatter(Player currentBatter);

    Player getPitcher(Team team);
}
