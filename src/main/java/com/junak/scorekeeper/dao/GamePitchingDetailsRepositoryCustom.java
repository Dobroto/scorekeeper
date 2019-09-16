package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface GamePitchingDetailsRepositoryCustom {
    GamePitchingDetails getGamePitchingDetails(Player pitcher, Game game);

    List<GamePitchingDetails> getGamePitchingDetailsList(Game game);

    List<GamePitchingDetails> getGamePitchingDetailsList(Player player);
}
