package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface GameHittingDetailsRepositoryCustom {
    GameHittingDetails getGameHittingDetails(Player player, Game game);

    List<GameHittingDetails> getGameHittingDetailsList(Game game);

    List<GameHittingDetails> getGameHittingDetailsList(Player player);
}
