package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.errors.PlayerNotFoundException;
import com.junak.scorekeeper.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerRestController {
    private PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService thePlayerService) {
        playerService = thePlayerService;
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
            throw new PlayerNotFoundException("Player id not found - " + playerId);
        }

        return thePlayer;
    }

    // add mapping for POST /players - add new employee

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
            throw new PlayerNotFoundException("Player id not found - " + playerId);
        }

        playerService.deleteById(playerId);

        return "Deleted player id - " + playerId;
    }
}
