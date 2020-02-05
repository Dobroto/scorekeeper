package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import com.junak.scorekeeper.entity.Play;

import java.util.List;

public interface PlayService {

    Play findById(int id);

    Play save(Play play);

    void deleteById(int id);

    List<Play> getAllPlaysInInning(Inning inning);

    List<Play> getAllPlaysInGame(Game game);
}
