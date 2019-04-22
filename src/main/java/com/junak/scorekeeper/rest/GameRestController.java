package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.rest.error.game_error.GameNotFoundException;
import com.junak.scorekeeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameRestController {
    private GameService gameService;

    @Autowired
    public GameRestController (GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public List<Game> findAll() {
        return gameService.findAll();
    }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable int gameId) {

        Game theGame = gameService.findById(gameId);

        if (theGame == null) {
            throw new GameNotFoundException("Game id not found - " + gameId);
        }

        return theGame;
    }

    @PostMapping("/games")
    public Game addGame(@RequestBody Game theGame) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        theGame.setId(0);

        gameService.save(theGame);

        return theGame;
    }

    @PutMapping("/games")
    public Game updateGame(@RequestBody Game theGame) {

        gameService.save(theGame);

        return theGame;
    }

    @DeleteMapping("/games/{gameId}")
    public String deleteGame(@PathVariable int gameId) {

        Game tempGame = gameService.findById(gameId);

        // throw exception if null

        if (tempGame == null) {
            throw new GameNotFoundException("Game id not found - " + gameId);
        }

        gameService.deleteById(gameId);

        return "Deleted game id - " + gameId;
    }
}
