package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface GameHittingDetailsService {
    List<GameHittingDetails> findAll();

    GameHittingDetails findById(int id);

    GameHittingDetails save(GameHittingDetails gameHittingDetails);

    void deleteById(int id);

    GameHittingDetails getGameHittingDetails(Player player, Game game);

    List<GameHittingDetails> getGameHittingDetailsList(Game game);

    List<GameHittingDetails> getGameHittingDetailsList(Player player);
}
