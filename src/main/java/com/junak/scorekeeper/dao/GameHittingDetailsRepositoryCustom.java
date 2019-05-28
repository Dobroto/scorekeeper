package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;

public interface GameHittingDetailsRepositoryCustom {
    GameHittingDetails getGameHittingDetails(Player batter, Game game);
}
