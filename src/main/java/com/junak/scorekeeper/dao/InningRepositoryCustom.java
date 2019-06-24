package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;

public interface InningRepositoryCustom {
    Inning getCurrentInning(Game game);
}
