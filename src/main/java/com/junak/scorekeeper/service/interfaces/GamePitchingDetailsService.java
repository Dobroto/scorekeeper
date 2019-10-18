package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;

import java.util.List;

public interface GamePitchingDetailsService {
    List<GamePitchingDetails> findAll();

    GamePitchingDetails findById(int id);

    GamePitchingDetails save(GamePitchingDetails gamePitchingDetails);

    void deleteById(int id);

    GamePitchingDetails getGamePitchingDetails(Player pitcher, Game game);

    List<GamePitchingDetails> getGamePitchingDetailsList(Game game);

    List<GamePitchingDetails> getGamePitchingDetailsList(Player player);
}
