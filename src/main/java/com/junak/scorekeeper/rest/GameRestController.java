package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.Constants;
import com.junak.scorekeeper.entity.*;
import com.junak.scorekeeper.rest.error.game_error.GameNotFoundException;
import com.junak.scorekeeper.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameRestController {
    private GameService gameService;
    private PlayerService playerService;
    private TeamService teamService;
    private GameFieldingDetailsService gameFieldingDetailsService;
    private GameHittingDetailsService gameHittingDetailsService;
    private GamePitchingDetailsService gamePitchingDetailsService;
    private PlayerFieldingDetailsService playerFieldingDetailsService;
    private PlayerHittingDetailsService playerHittingDetailsService;
    private PlayerPitchingDetailsService playerPitchingDetailsService;

    @Autowired
    public GameRestController(GameService gameService, PlayerService playerService, TeamService teamService,
                              GameFieldingDetailsService gameFieldingDetailsService,
                              GameHittingDetailsService gameHittingDetailsService,
                              GamePitchingDetailsService gamePitchingDetailsService,
                              PlayerFieldingDetailsService playerFieldingDetailsService,
                              PlayerHittingDetailsService playerHittingDetailsService,
                              PlayerPitchingDetailsService playerPitchingDetailsService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameFieldingDetailsService = gameFieldingDetailsService;
        this.gameHittingDetailsService = gameHittingDetailsService;
        this.gamePitchingDetailsService = gamePitchingDetailsService;
        this.playerFieldingDetailsService = playerFieldingDetailsService;
        this.playerHittingDetailsService = playerHittingDetailsService;
        this.playerPitchingDetailsService = playerPitchingDetailsService;
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

    @PostMapping("/games/{homeTeamId}/{visitorTeamId}/createNewGame")
    public void createNewGame(@PathVariable int homeTeamId, @PathVariable int visitorTeamId) {
        Team homeTeam = teamService.findById(homeTeamId);
        Team visitorTeam = teamService.findById(visitorTeamId);

        Game theGame = new Game();
        theGame.setHomeTeam(homeTeam);
        theGame.setVisitorTeam(visitorTeam);
        gameService.save(theGame);

        initializePlayerDetails(homeTeam);
        initializePlayerDetails(visitorTeam);
    }

    private void initializePlayerDetails(Team team) {
        List<Player> players = team.getPlayers();
        for (Player player : players) {
            initializePlayerHittingDetails(player);
            initializePlayerPitchingDetails(player);
            initializePlayerFieldingDetails(player);
        }
    }

    private void initializePlayerHittingDetails(Player player) {
        if (player.getPlayerHittingDetails() == null) {
            PlayerHittingDetails hittingDetails = new PlayerHittingDetails();
            hittingDetails.setPlayer(player);
            playerHittingDetailsService.save(hittingDetails);
            player.setPlayerHittingDetails(hittingDetails);
            playerService.save(player);
        }
    }

    private void initializePlayerPitchingDetails(Player player) {
        if (player.getPlayerPitchingDetails() == null) {
            PlayerPitchingDetails pitchingDetails = new PlayerPitchingDetails();
            pitchingDetails.setPlayer(player);
            playerPitchingDetailsService.save(pitchingDetails);
            player.setPlayerPitchingDetails(pitchingDetails);
            playerService.save(player);
        }
    }

    private void initializePlayerFieldingDetails(Player player) {
        if (player.getPlayerFieldingDetails() == null) {
            PlayerFieldingDetails fieldingDetails = new PlayerFieldingDetails();
            fieldingDetails.setPlayer(player);
            playerFieldingDetailsService.save(fieldingDetails);
            player.setPlayerFieldingDetails(fieldingDetails);
            playerService.save(player);
        }
    }

    @PutMapping("/games/{gameId}/play")
    public void startGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        Date currentDate = new Date();
        theGame.setStartTimeOfGame(currentDate);
        Team homeTeam = theGame.getHomeTeam();
        Team visitorTeam = theGame.getVisitorTeam();

        //TODO initialize all game details to the starting players

        initializeGameDetails(homeTeam, theGame);
        gameService.save(theGame);
    }

    private void initializeGameDetails(Team team, Game theGame) {
        List<Player> players = team.getPlayers();
        for (Player player : players) {
            initializeGameHittingDetails(player, theGame);
//            initializeGamePitchingDetails(player, theGame);
//            initializeGameFieldingDetails(player, theGame);
        }

//
//        //find batter
//        Game theGame = gameService.findById(gameId);
//        Team homeTeam = theGame.getHomeTeam();
//        Team visitorTeam = theGame.getVisitorTeam();
//        Player batter = getCurrentBatter(visitorTeam);
//        //create game hitting details for batter
//        GameHittingDetails batterGameHittingDetails = new GameHittingDetails();
//        batterGameHittingDetails.setPlayer(batter);
//        batterGameHittingDetails.setGame(theGame);
//        //increasing game (G) statistics to batter
//        batterGameHittingDetails.setGames(batterGameHittingDetails.getGames() + 1);
//        batter.getPlayerHittingDetails();
//
//        gameHittingDetailsService.save(batterGameHittingDetails);
    }

    private void initializeGameHittingDetails(Player player, Game theGame) {
        if (player.isStarter()) {
            GameHittingDetails gameHittingDetails = new GameHittingDetails();
            gameHittingDetails.setPlayer(player);
            gameHittingDetails.setGame(theGame);
        }
    }

    private Player getCurrentBatter(Team offensiveTeam) {
        List<Player> players = offensiveTeam.getPlayers();
        int lowestBattingOrder = 10;
        Player batter = new Player();
        for (Player player : players) {
            if ((player.getBattingOrder() < lowestBattingOrder) && (player.getBattingOrder() != 0)) {
                batter = player;
                lowestBattingOrder = player.getBattingOrder();
            }
        }
        return batter;
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
        int currentNumberOfBalls = batter.getBallCount();
        currentNumberOfBalls++;
        if (currentNumberOfBalls > 3) {
            walk(gameId, pitcherId, batterId);
            return;
        }
        batter.setBallCount(currentNumberOfBalls);
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

    //not finished
    private void pushRunnersWithOneBase(Player batter) {
        Player firstBaseRunner = getFirstBaseRunner(batter);
        Player secondBaseRunner = getSecondBaseRunner(batter);
        Player thirdBaseRunner = getThirdBaseRunner(batter);

        batter.setOffencePosition(Constants.runnerFirstBase);
        //increase number of walks by batter
        PlayerHittingDetails batterHittingDetails = batter.getPlayerHittingDetails();
        batterHittingDetails.setBaseForBalls(batterHittingDetails.getBaseForBalls() + 1);


        if (firstBaseRunner != null) {
            firstBaseRunner.setOffencePosition(Constants.runnerSecondBase);
            playerService.save(firstBaseRunner);
            if (secondBaseRunner != null) {
                secondBaseRunner.setOffencePosition(Constants.runnerThirdBase);
                playerService.save(secondBaseRunner);
                if (thirdBaseRunner != null) {
                    thirdBaseRunner.setOffencePosition(Constants.runnerHomeBase);
                    //increase runs by third base runner
                    PlayerHittingDetails thirdBaseRunnerHittingDetails = thirdBaseRunner.getPlayerHittingDetails();
                    thirdBaseRunnerHittingDetails.setRuns(thirdBaseRunnerHittingDetails.getRuns() + 1);
                    //increase RBI by batter
                    batterHittingDetails.setRunBattedIn(batterHittingDetails.getRunBattedIn() + 1);
                    playerService.save(thirdBaseRunner);
                }
            }
        }
        playerService.save(batter);
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
