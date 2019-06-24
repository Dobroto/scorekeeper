package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameFieldingDetailsService;
import com.junak.scorekeeper.service.interfaces.GameHittingDetailsService;
import com.junak.scorekeeper.service.interfaces.GamePitchingDetailsService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerRestController {
    private PlayerService playerService;
    private GameHittingDetailsService gameHittingDetailsService;
    private GamePitchingDetailsService gamePitchingDetailsService;
    private GameFieldingDetailsService gameFieldingDetailsService;


    @Autowired
    public PlayerRestController(PlayerService playerService, GameHittingDetailsService gameHittingDetailsService,
                                GamePitchingDetailsService gamePitchingDetailsService,
                                GameFieldingDetailsService gameFieldingDetailsService) {
        this.playerService = playerService;
        this.gameHittingDetailsService = gameHittingDetailsService;
        this.gamePitchingDetailsService = gamePitchingDetailsService;
        this.gameFieldingDetailsService = gameFieldingDetailsService;
    }

    // expose "/players" and return list of players
    @GetMapping("/players")
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @GetMapping("/players/team/{teamId}")
    public List<Player> findAllTeamPlayers(@PathVariable int teamId) {
        return playerService.findAllTeamPlayers(teamId);
    }

    // add mapping for GET /players/{playerId}

    @GetMapping("/players/{playerId}")
    public Player getPlayer(@PathVariable int playerId) {

        Player thePlayer = playerService.findById(playerId);

        if (thePlayer == null) {
            throw new GameNotFoundException("Player id not found - " + playerId);
        }

        return thePlayer;
    }

    // add mapping for POST /players - add new player

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player thePlayer) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        thePlayer.setId(0);

        playerService.save(thePlayer);

        return thePlayer;
    }

    // add mapping for PUT /players - update existing player

    @PutMapping("/players")
    public Player updatePlayer(@RequestBody Player thePlayer) {

        playerService.save(thePlayer);

        return thePlayer;
    }

    // add mapping for DELETE /players/{playerId} - delete player

    @DeleteMapping("/players/{playerId}")
    public String deletePlayer(@PathVariable int playerId) {

        Player tempPlayer = playerService.findById(playerId);

        // throw exception if null

        if (tempPlayer == null) {
            throw new GameNotFoundException("Player id not found - " + playerId);
        }

        deleteGameHittingDetails(tempPlayer);
        deleteGamePitchingDetails(tempPlayer);
        deleteGameFieldingDetails(tempPlayer);

        playerService.deleteById(playerId);

        return "Deleted player id - " + playerId;
    }

    private void deleteGameHittingDetails(Player player) {
        if (player.getGameHittingDetails().size() > 0) {
            List<GameHittingDetails> gameHittingDetailsList = player.getGameHittingDetails();

            GameHittingDetails[] detailsToDeleteArr = new GameHittingDetails[gameHittingDetailsList.size()];

            for (int i = 0; i < detailsToDeleteArr.length; i++) {

                GameHittingDetails detailsToDelete = gameHittingDetailsList.get(i);
                detailsToDelete.setPlayer(null);
                detailsToDelete.setGame(null);
                gameHittingDetailsService.save(detailsToDelete);
                //TODO Can't delete details
//                gameHittingDetailsService.deleteById(detailsToDelete.getId());
            }
        }
    }

    private void deleteGamePitchingDetails(Player player) {
        if (player.getGamePitchingDetails().size() > 0) {
            List<GamePitchingDetails> gamePitchingDetailsList = player.getGamePitchingDetails();

            GamePitchingDetails[] detailsToDeleteArr = new GamePitchingDetails[gamePitchingDetailsList.size()];

            for (int i = 0; i < detailsToDeleteArr.length; i++) {

                GamePitchingDetails detailsToDelete = gamePitchingDetailsList.get(i);
                detailsToDelete.setPlayer(null);
                detailsToDelete.setGame(null);
                gamePitchingDetailsService.save(detailsToDelete);
                //TODO Can't delete details
//                gamePitchingDetailsService.deleteById(detailsToDelete.getId());
            }
        }
    }

    private void deleteGameFieldingDetails(Player player) {
        if (player.getGameFieldingDetails().size() > 0) {
            List<GameFieldingDetails> gameFieldingDetailsList = player.getGameFieldingDetails();

            GameFieldingDetails[] detailsToDeleteArr = new GameFieldingDetails[gameFieldingDetailsList.size()];

            for (int i = 0; i < detailsToDeleteArr.length; i++) {

                GameFieldingDetails detailsToDelete = gameFieldingDetailsList.get(i);
                detailsToDelete.setPlayer(null);
                detailsToDelete.setGame(null);
                gameFieldingDetailsService.save(detailsToDelete);
                //TODO Can't delete details
//                gameFieldingDetailsService.deleteById(detailsToDelete.getId());
            }
        }
    }
}
