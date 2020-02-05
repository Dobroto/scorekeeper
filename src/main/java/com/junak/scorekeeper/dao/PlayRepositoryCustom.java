package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import com.junak.scorekeeper.entity.Play;

import java.util.List;

public interface PlayRepositoryCustom {

    List<Play> getAllPlaysInInning(Inning inning);

    List<Play> getAllPlaysInGame(Game game);
}
