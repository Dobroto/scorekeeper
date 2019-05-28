package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.Constants;
import com.junak.scorekeeper.entity.*;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
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

        Player[] arrPlayers = new Player[players.size()];

        for (int i = 0; i < players.size(); i++) {
            arrPlayers[i] = players.get(i);
        }

        for (Player player : arrPlayers) {
            if (player.getPlayerHittingDetails() == null) {
                PlayerHittingDetails hittingDetails = createPlayerHittingDetails(player);
                player.setPlayerHittingDetails(hittingDetails);
            }
            if (player.getPlayerPitchingDetails() == null) {
                PlayerPitchingDetails pitchingDetails = createPlayerPitchingDetails(player);
                player.setPlayerPitchingDetails(pitchingDetails);
            }
            if (player.getPlayerFieldingDetails() == null) {
                PlayerFieldingDetails fieldingDetails = createPlayerFieldingDetails(player);
                player.setPlayerFieldingDetails(fieldingDetails);
            }
            playerService.save(player);
        }
    }

    private PlayerHittingDetails createPlayerHittingDetails(Player player) {
        PlayerHittingDetails hittingDetails = new PlayerHittingDetails();
        hittingDetails.setPlayer(player);
        playerHittingDetailsService.save(hittingDetails);
        return hittingDetails;
    }

    private PlayerPitchingDetails createPlayerPitchingDetails(Player player) {
        PlayerPitchingDetails pitchingDetails = new PlayerPitchingDetails();
        pitchingDetails.setPlayer(player);
        playerPitchingDetailsService.save(pitchingDetails);
        return pitchingDetails;
    }

    private PlayerFieldingDetails createPlayerFieldingDetails(Player player) {
        PlayerFieldingDetails fieldingDetails = new PlayerFieldingDetails();
        fieldingDetails.setPlayer(player);
        playerFieldingDetailsService.save(fieldingDetails);
        return fieldingDetails;
    }

    @PutMapping("/games/{gameId}/play")
    public void startGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        Date currentDate = new Date();
        theGame.setStartTimeOfGame(currentDate);
        Team homeTeam = theGame.getHomeTeam();
        Team visitorTeam = theGame.getVisitorTeam();
        gameService.save(theGame);
        initializeGameDetails(homeTeam, visitorTeam, theGame);
    }

    private void initializeGameDetails(Team homeTeam, Team visitorTeam, Game theGame) {
        Player batter = playerService.getStartingBatter(visitorTeam);
        initializeGameHittingDetails(batter, theGame);
        initializeGamePitchingDetails(homeTeam, theGame);
        initializeGameFieldingDetails(homeTeam, theGame);
    }

    private void initializeGameHittingDetails(Player batter, Game theGame) {
        batter.setOffencePosition(Constants.batter);
        playerService.save(batter);

        GameHittingDetails gameHittingDetails = new GameHittingDetails();
        gameHittingDetails.setPlayer(batter);
        gameHittingDetails.setGame(theGame);
        gameHittingDetails.setPlateAppearences(1);
        gameHittingDetailsService.save(gameHittingDetails);

        //record the game and plate appearance statistics
        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();
        int currentGamesStatistic = playerHittingDetails.getGames();
        playerHittingDetails.setGames(currentGamesStatistic + 1);
        int currentPlateAppearance = playerHittingDetails.getPlateAppearences();
        playerHittingDetails.setPlateAppearences(currentPlateAppearance + 1);
        playerHittingDetailsService.save(playerHittingDetails);
    }

    private void initializeGamePitchingDetails(Team team, Game theGame) {
        Player pitcher = getCurrentPitcher(team);
        GamePitchingDetails gamePitchingDetails = new GamePitchingDetails();
        gamePitchingDetails.setPlayer(pitcher);
        gamePitchingDetails.setGame(theGame);
        gamePitchingDetailsService.save(gamePitchingDetails);

        //record the "game" and "game started" statistics
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        int currentGamesStatistic = playerPitchingDetails.getGames();
        playerPitchingDetails.setGames(currentGamesStatistic + 1);
        int currentStartedGamesStatistic = playerPitchingDetails.getGamesStarted();
        playerPitchingDetails.setGamesStarted(currentStartedGamesStatistic + 1);
        playerPitchingDetailsService.save(playerPitchingDetails);
    }

    private void initializeGameFieldingDetails(Team team, Game theGame) {
        List<Player> players = team.getPlayers();

        Player[] arrPlayers = new Player[players.size()];

        for (int i = 0; i < players.size(); i++) {
            arrPlayers[i] = players.get(i);
        }

        for (Player player : arrPlayers) {
            if ((player.getDefencePosition() != null) && (player.isStarter())) {
                GameFieldingDetails gameFieldingDetails = new GameFieldingDetails();
                gameFieldingDetails.setPlayer(player);
                gameFieldingDetails.setGame(theGame);
                gameFieldingDetailsService.save(gameFieldingDetails);

                //record the "games played" and "game started" statistics
                PlayerFieldingDetails playerFieldingDetails = player.getPlayerFieldingDetails();
                int currentGamesPlayed = playerFieldingDetails.getGamesPlayed();
                playerFieldingDetails.setGamesPlayed(currentGamesPlayed + 1);
                int currentGamesStarted = playerFieldingDetails.getGamesStarted();
                playerFieldingDetails.setGamesPlayed(currentGamesStarted + 1);
                playerFieldingDetailsService.save(playerFieldingDetails);
            }
        }
    }

    //TODO take it from the database
    private Player getCurrentPitcher(Team defensiveTeam) {
        List<Player> players = defensiveTeam.getPlayers();
        for (Player player : players) {
            if (player.getDefencePosition().toLowerCase().equals(Constants.defendingPositionPitcher)) {
                return player;
            }
        }
        throw new RuntimeException("There is no pitcher!");
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
            batter.setBallCount(0);
            playerService.save(batter);
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
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);

        //assign a walk to the pitcher on PlayerPitchingDetails
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        int currentOverallBaseOnBalls = playerPitchingDetails.getBasesOnBalls();
        playerPitchingDetails.setBasesOnBalls(currentOverallBaseOnBalls + 1);
        playerPitchingDetailsService.save(playerPitchingDetails);

        //assign a walk to the pitcher on GamePitchingDetails
        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        int currentGameBaseOnBalls = gamePitchingDetails.getBasesOnBalls();
        gamePitchingDetails.setBasesOnBalls(currentGameBaseOnBalls + 1);
        gamePitchingDetailsService.save(gamePitchingDetails);

        //increase number of walks by batter - player details
        PlayerHittingDetails batterPlayerHittingDetails = batter.getPlayerHittingDetails();
        batterPlayerHittingDetails.setBaseForBalls(batterPlayerHittingDetails.getBaseForBalls() + 1);
        playerHittingDetailsService.save(batterPlayerHittingDetails);

        //increase number of walks by batter - game details
        GameHittingDetails batterGameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        batterGameHittingDetails.setBaseForBalls(batterGameHittingDetails.getBaseForBalls() + 1);
        gameHittingDetailsService.save(batterGameHittingDetails);

        Team offensiveTeam = batter.getTeam();
        pushRunnersWithOneBase(batter, pitcher, offensiveTeam, theGame);

        //TODO livescore
    }

    private void pushRunnersWithOneBase(Player batter, Player pitcher, Team offensiveTeam, Game theGame) {
        Player firstBaseRunner = playerService.getFirstBaseRunner(offensiveTeam);
        Player secondBaseRunner = playerService.getSecondBaseRunner(offensiveTeam);
        Player thirdBaseRunner = playerService.getThirdBaseRunner(offensiveTeam);

        batter.setOffencePosition(Constants.runnerFirstBase);
        playerService.save(batter);

        Player nextBatter = playerService.getNextBatter(batter);
        initializeGameHittingDetails(nextBatter, theGame);

        if (firstBaseRunner != null) {
            firstBaseRunner.setOffencePosition(Constants.runnerSecondBase);
            playerService.save(firstBaseRunner);
            if (secondBaseRunner != null) {
                secondBaseRunner.setOffencePosition(Constants.runnerThirdBase);
                playerService.save(secondBaseRunner);
                if (thirdBaseRunner != null) {
                    thirdBaseRunner.setOffencePosition(Constants.runnerHomeBase);
                    playerService.save(thirdBaseRunner);
                    scoreARun(thirdBaseRunner, batter, pitcher, theGame, true, true);
                }
            }
        }
    }

    private void scoreARun(Player runner, Player batter, Player pitcher, Game theGame,
                           boolean withBatterHelp, boolean isEarned) {
        //increase player runs by third base runner
        PlayerHittingDetails thirdBaseRunnerPlayerHittingDetails = runner.getPlayerHittingDetails();
        thirdBaseRunnerPlayerHittingDetails.setRuns(thirdBaseRunnerPlayerHittingDetails.getRuns() + 1);
        playerHittingDetailsService.save(thirdBaseRunnerPlayerHittingDetails);

        //increase game runs by third base runner
        GameHittingDetails thirdBaseRunnerGameHittingDetails = gameHittingDetailsService.getGameHittingDetails(runner, theGame);
        thirdBaseRunnerGameHittingDetails.setRuns(thirdBaseRunnerGameHittingDetails.getRuns() + 1);
        gameHittingDetailsService.save(thirdBaseRunnerGameHittingDetails);

        if (withBatterHelp) {
            //increase player RBI by batter
            PlayerHittingDetails batterPlayerHittingDetails = batter.getPlayerHittingDetails();
            batterPlayerHittingDetails.setRunBattedIn(batterPlayerHittingDetails.getRunBattedIn() + 1);
            playerHittingDetailsService.save(batterPlayerHittingDetails);

            //increase player RBI by batter
            GameHittingDetails batterGameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
            batterGameHittingDetails.setRunBattedIn(batterGameHittingDetails.getRunBattedIn() + 1);
            gameHittingDetailsService.save(batterGameHittingDetails);
        }

        //increase runs by pitcher (player details)
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        playerPitchingDetails.setRuns(playerPitchingDetails.getRuns() + 1);

        //increase runs by pitcher (game details)
        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        gamePitchingDetails.setRuns(gamePitchingDetails.getRuns() + 1);

        //if its by a pitcher mistake
        if (isEarned) {
            playerPitchingDetails.setEarnedRuns(playerPitchingDetails.getEarnedRuns() + 1);

            gamePitchingDetails.setEarnedRuns(gamePitchingDetails.getEarnedRuns() + 1);
        }

        playerPitchingDetailsService.save(playerPitchingDetails);
        gamePitchingDetailsService.save(gamePitchingDetails);
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
