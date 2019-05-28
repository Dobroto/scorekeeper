package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.entity.Player;

public interface GameFieldingDetailsRepositoryCustom {
    GameFieldingDetails getGameFieldingDetails(Player fielder, Game game);
}
