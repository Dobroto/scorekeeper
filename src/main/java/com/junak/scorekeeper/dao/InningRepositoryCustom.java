package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;

import java.util.List;

public interface InningRepositoryCustom {
    Inning getCurrentInning(Game game);

    List<Inning> getInningsList(Game game);
}
