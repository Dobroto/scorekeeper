package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;

import java.util.List;

public interface InningService {
    List<Inning> findAll();

    Inning findById(int id);

    Inning save(Inning inning);

    void deleteById(int id);

    Inning getCurrentInning(Game game);

    List<Inning> getInningsList(Game game);
}
