package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;

public interface GamePitchingDetailsRepositoryCustom {
    GamePitchingDetails getGamePitchingDetails(Player pitcher, Game game);
}
