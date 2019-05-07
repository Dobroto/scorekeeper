package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.Constants;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.rest.error.game_error.GameNotFoundException;
import com.junak.scorekeeper.service.GameService;
import com.junak.scorekeeper.service.ActionReverser;
import com.junak.scorekeeper.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameRestController {
    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public GameRestController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
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

    @PutMapping("/games/{gameId}/play")
    public void startGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        Date currentDate = new Date();
        theGame.setStartTimeOfGame(currentDate);
        gameService.save(theGame);
    }

    @PutMapping("/games/{gameId}/endGame")
    public void endGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        Date currentDate = new Date();
        theGame.setEndTimeOfGame(currentDate);
        gameService.save(theGame);
    }

    @PutMapping("/games/{gameId}/undo")
    public void undo(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        String lastCommand = theGame.getLastCommand();
        ActionReverser actionReverser = new ActionReverser();
        actionReverser.undo(lastCommand);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/ball")
    public void ball(@PathVariable int gameId,
                     @PathVariable int pitcherId, @PathVariable int batterId) {
        Game theGame = gameService.findById(gameId);
        theGame.setLastCommand("ball");
        Player batter = playerService.findById(batterId);
        int currentNumberOfBalls = batter.getBallNumber();
        currentNumberOfBalls++;
        if (currentNumberOfBalls > 3) {
            walk(gameId, pitcherId, batterId);
            return;
        }
        batter.setBallNumber(currentNumberOfBalls);
        playerService.save(batter);
        //TODO livescore
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/walk")
    public void walk(@PathVariable int gameId,
                     @PathVariable int pitcherId, @PathVariable int batterId) {
        Game theGame = gameService.findById(gameId);
        theGame.setLastCommand("walk");
        Player batter = playerService.findById(batterId);
        pushRunnersWithOneBase(batter);
    }

    private void pushRunnersWithOneBase(Player batter) {
        Player firstBaseRunner = getFirstBaseRunner(batter);
        Player secondBaseRunner = getSecondBaseRunner(batter);
        Player thirdBaseRunner = getThirdBaseRunner(batter);

        batter.setOffencePosition(Constants.runnerFirstBase);
        playerService.save(batter);
        if (firstBaseRunner != null) {
            firstBaseRunner.setOffencePosition(Constants.runnerSecondBase);
            playerService.save(firstBaseRunner);
            if (secondBaseRunner != null) {
                secondBaseRunner.setOffencePosition(Constants.runnerThirdBase);
                playerService.save(secondBaseRunner);
                if (thirdBaseRunner != null) {
                    thirdBaseRunner.setOffencePosition(Constants.runnerHomeBase);
                }
            }
        }
    }

    private Player getFirstBaseRunner(Player batter) {
        Team offensiveTeam = batter.getTeam();
        List<Player> offensivePlayers = offensiveTeam.getPlayers();
        Player firstBaseRunner = null;
        for (Player player : offensivePlayers) {
            if (player.getOffencePosition().equals(Constants.runnerFirstBase)) {
                firstBaseRunner = player;
                break;
            }
        }
        return firstBaseRunner;
    }

    private Player getSecondBaseRunner(Player batter) {
        Team offensiveTeam = batter.getTeam();
        List<Player> offensivePlayers = offensiveTeam.getPlayers();
        Player secondBaseRunner = null;
        for (Player player : offensivePlayers) {
            if (player.getOffencePosition().equals(Constants.runnerSecondBase)) {
                secondBaseRunner = player;
                break;
            }
        }
        return secondBaseRunner;
    }

    private Player getThirdBaseRunner(Player batter) {
        Team offensiveTeam = batter.getTeam();
        List<Player> offensivePlayers = offensiveTeam.getPlayers();
        Player thirdBaseRunner = null;
        for (Player player : offensivePlayers) {
            if (player.getOffencePosition().equals(Constants.runnerThirdBase)) {
                thirdBaseRunner = player;
                break;
            }
        }
        return thirdBaseRunner;
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strike")
    public void strike(@PathVariable int gameId,
                       @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO
    }

    @PutMapping("/games/{gameId}/{batterId}/foul")
    public void foul(@PathVariable int gameId, @PathVariable int batterId) {
        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/out")
    public void strikeOut(@PathVariable int gameId,
                          @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO
    }

    //when the batter doesn't swing the bat and he is out.
    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strikeOutLooking")
    public void strikeOutLooking(@PathVariable int gameId,
                                 @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/groundOut")
    public void groundOut(@PathVariable int gameId,
                          @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO
    }

    //offensive player reaching a base
    // due to the defense's attempt to put out another baserunner
    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/outedRunner/fieldersChoice")
    public void fieldersChoice(@PathVariable int gameId,
                               @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO
    }
}
