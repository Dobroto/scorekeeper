package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.Constants;
import com.junak.scorekeeper.entity.*;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.*;
import com.junak.scorekeeper.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameRestController {
    private static final Logger logger = LoggerFactory.getLogger(GameRestController.class);

    private GameService gameService;
    private PlayerService playerService;
    private TeamService teamService;
    private InningService inningService;
    private FinalResultService finalResultService;
    private GameFieldingDetailsService gameFieldingDetailsService;
    private GameHittingDetailsService gameHittingDetailsService;
    private GamePitchingDetailsService gamePitchingDetailsService;
    private PlayerFieldingDetailsService playerFieldingDetailsService;
    private PlayerHittingDetailsService playerHittingDetailsService;
    private PlayerPitchingDetailsService playerPitchingDetailsService;

    @Autowired
    public GameRestController(GameService gameService, PlayerService playerService,
                              TeamService teamService, InningService inningService,
                              FinalResultService finalResultService,
                              GameFieldingDetailsService gameFieldingDetailsService,
                              GameHittingDetailsService gameHittingDetailsService,
                              GamePitchingDetailsService gamePitchingDetailsService,
                              PlayerFieldingDetailsService playerFieldingDetailsService,
                              PlayerHittingDetailsService playerHittingDetailsService,
                              PlayerPitchingDetailsService playerPitchingDetailsService) {

        this.gameService = gameService;
        this.playerService = playerService;
        this.teamService = teamService;
        this.inningService = inningService;
        this.finalResultService = finalResultService;
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
        logger.info("Created new game.");

        Inning firstInning = new Inning();
        firstInning.setGame(theGame);
        firstInning.setInningNumber(1);
        inningService.save(firstInning);
        logger.info("Saved first inning.");

        FinalResult finalResult = new FinalResult();
        finalResult.setGame(theGame);
        finalResult = finalResultService.save(finalResult);
        theGame.setFinalResult(finalResult);
        gameService.save(theGame);
        logger.info("Initiated final result.");

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
        logger.info("Initialized player details.");
    }

    private PlayerHittingDetails createPlayerHittingDetails(Player player) {
        PlayerHittingDetails hittingDetails = new PlayerHittingDetails();
        hittingDetails.setPlayer(player);
        return playerHittingDetailsService.save(hittingDetails);
    }

    private PlayerPitchingDetails createPlayerPitchingDetails(Player player) {
        PlayerPitchingDetails pitchingDetails = new PlayerPitchingDetails();
        pitchingDetails.setPlayer(player);
        return playerPitchingDetailsService.save(pitchingDetails);
    }

    private PlayerFieldingDetails createPlayerFieldingDetails(Player player) {
        PlayerFieldingDetails fieldingDetails = new PlayerFieldingDetails();
        fieldingDetails.setPlayer(player);
        return playerFieldingDetailsService.save(fieldingDetails);
    }

    @PutMapping("/games/{gameId}/play")
    public void startGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        Date currentDate = new Date();
        theGame.setStartTimeOfGame(currentDate);
        Team homeTeam = theGame.getHomeTeam();
        Team visitorTeam = theGame.getVisitorTeam();
        gameService.save(theGame);
        logger.info("Game started.");

        initializeGameDetails(homeTeam, visitorTeam, theGame);
        logger.info("Game details initialized.");
    }

    private void initializeGameDetails(Team defendingTeam, Team offensiveTeam, Game theGame) {
        Player batter = playerService.getStartingBatter(offensiveTeam);
        initializeGameHittingDetails(batter, theGame);
        initializeGamePitchingDetails(defendingTeam, theGame);
        initializeGameFieldingDetails(defendingTeam, theGame);
    }

    private void initializeGameHittingDetails(Player batter, Game theGame) {
        batter.setOffencePosition(Constants.batter);
        playerService.save(batter);

        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();

        if (gameHittingDetailsService.getGameHittingDetails(batter, theGame) == null) {
            GameHittingDetails gameHittingDetails = new GameHittingDetails();
            gameHittingDetails.setPlayer(batter);
            gameHittingDetails.setGame(theGame);
            gameHittingDetails.setPlateAppearences(1);
            gameHittingDetailsService.save(gameHittingDetails);
            logger.info("Created game hitting details of batter with id {}.", batter.getId());

            //record the game appearance statistic
            int currentGamesStatistic = playerHittingDetails.getGames();
            playerHittingDetails.setGames(currentGamesStatistic + 1);
            logger.info("Updated game appearances statistic in player hitting details of batter with id {}.", batter.getId());
        }

        //record the plate appearance statistic
        int currentPlateAppearance = playerHittingDetails.getPlateAppearances();
        playerHittingDetails.setPlateAppearances(currentPlateAppearance + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Updated plate appearances statistic in player hitting details of batter with id {}.", batter.getId());
    }

    private void initializeGamePitchingDetails(Team team, Game theGame) {
        //TODO This should be redone in case of substitution
        Player pitcher = playerService.getPitcher(team);

        if (gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame) == null) {
            GamePitchingDetails gamePitchingDetails = new GamePitchingDetails();
            gamePitchingDetails.setPlayer(pitcher);
            gamePitchingDetails.setGame(theGame);
            gamePitchingDetailsService.save(gamePitchingDetails);
            logger.info("Created game pitching details of pitcher with id {}", pitcher.getId());
        }

        //record the "game" and "game started" statistics
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        int currentGamesStatistic = playerPitchingDetails.getGames();
        playerPitchingDetails.setGames(currentGamesStatistic + 1);
        int currentStartedGamesStatistic = playerPitchingDetails.getGamesStarted();
        playerPitchingDetails.setGamesStarted(currentStartedGamesStatistic + 1);
        playerPitchingDetailsService.save(playerPitchingDetails);
        logger.info("Updated games and started games statistics in player pitching details of pitcher with id {}",
                pitcher.getId());
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
                logger.info("Created game fielding details of player with id {}.", player.getId());

                //record the "games played" and "game started" statistics
                PlayerFieldingDetails playerFieldingDetails = player.getPlayerFieldingDetails();
                int currentGamesPlayed = playerFieldingDetails.getGamesPlayed();
                playerFieldingDetails.setGamesPlayed(currentGamesPlayed + 1);
                int currentGamesStarted = playerFieldingDetails.getGamesStarted();
                playerFieldingDetails.setGamesPlayed(currentGamesStarted + 1);
                playerFieldingDetailsService.save(playerFieldingDetails);
                logger.info("Updated games played and games started statistics of player with id {}.", player.getId());
            }
        }
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
            logger.info("The pitcher threw four balls.");
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
        logger.info("A walk was assigned to the pitcher's player details. Player id - {}", pitcher.getId());

        //assign a walk to the pitcher on GamePitchingDetails
        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        int currentGameBaseOnBalls = gamePitchingDetails.getBasesOnBalls();
        gamePitchingDetails.setBasesOnBalls(currentGameBaseOnBalls + 1);
        gamePitchingDetailsService.save(gamePitchingDetails);
        logger.info("A walk was assigned to the pitcher's game details. Player id - {}", pitcher.getId());

        //increase number of walks by batter - player details
        PlayerHittingDetails batterPlayerHittingDetails = batter.getPlayerHittingDetails();
        batterPlayerHittingDetails.setBaseForBalls(batterPlayerHittingDetails.getBaseForBalls() + 1);
        playerHittingDetailsService.save(batterPlayerHittingDetails);
        logger.info("A walk was assigned to the batter's player details. Player id - {}", batter.getId());

        //increase number of walks by batter - game details
        GameHittingDetails batterGameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        batterGameHittingDetails.setBaseForBalls(batterGameHittingDetails.getBaseForBalls() + 1);
        gameHittingDetailsService.save(batterGameHittingDetails);
        logger.info("A walk was assigned to the batter's game details. Player id - {}", batter.getId());

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

        setNextBatter(batter, theGame);

        if (firstBaseRunner != null) {
            firstBaseRunner.setOffencePosition(Constants.runnerSecondBase);
            playerService.save(firstBaseRunner);
            logger.info("Batter with id {} pushed player with id {} from first base to second base.",
                    batter.getId(), firstBaseRunner.getId());

            if (secondBaseRunner != null) {
                secondBaseRunner.setOffencePosition(Constants.runnerThirdBase);
                playerService.save(secondBaseRunner);
                logger.info("Runner from first base with id {} pushed runner with id {} from second to third base.",
                        firstBaseRunner.getId(), secondBaseRunner.getId());

                if (thirdBaseRunner != null) {
                    thirdBaseRunner.setOffencePosition(Constants.runnerHomeBase);
                    playerService.save(thirdBaseRunner);
                    logger.info("Runner from second base with id {} pushed runner with id {} from third to home base.",
                            secondBaseRunner.getId(), thirdBaseRunner.getId());
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
        logger.info("Increase player runs by runner with id {}.", runner.getId());

        //increase game runs by third base runner
        GameHittingDetails thirdBaseRunnerGameHittingDetails = gameHittingDetailsService.getGameHittingDetails(runner, theGame);
        thirdBaseRunnerGameHittingDetails.setRuns(thirdBaseRunnerGameHittingDetails.getRuns() + 1);
        gameHittingDetailsService.save(thirdBaseRunnerGameHittingDetails);
        logger.info("Increase game runs by runner with id {}.", runner.getId());

        if (withBatterHelp) {
            //increase player RBI by batter
            PlayerHittingDetails batterPlayerHittingDetails = batter.getPlayerHittingDetails();
            batterPlayerHittingDetails.setRunBattedIn(batterPlayerHittingDetails.getRunBattedIn() + 1);
            playerHittingDetailsService.save(batterPlayerHittingDetails);

            //increase game RBI by batter
            GameHittingDetails batterGameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
            batterGameHittingDetails.setRunBattedIn(batterGameHittingDetails.getRunBattedIn() + 1);
            gameHittingDetailsService.save(batterGameHittingDetails);
            logger.info("Batter with id {} helped with this run.", batter.getId());
        }

        //increase runs by pitcher (player details)
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        playerPitchingDetails.setRuns(playerPitchingDetails.getRuns() + 1);
        logger.info("Increase player runs by pitcher with id {}.", pitcher.getId());

        //increase runs by pitcher (game details)
        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        gamePitchingDetails.setRuns(gamePitchingDetails.getRuns() + 1);
        logger.info("Increase game runs by pitcher with id {}.", pitcher.getId());

        //if its by a pitcher mistake
        if (isEarned) {
            playerPitchingDetails.setEarnedRuns(playerPitchingDetails.getEarnedRuns() + 1);

            gamePitchingDetails.setEarnedRuns(gamePitchingDetails.getEarnedRuns() + 1);
            logger.info("Increase runs caused by a pitcher's mistake.");
        }

        playerPitchingDetailsService.save(playerPitchingDetails);
        gamePitchingDetailsService.save(gamePitchingDetails);

        Inning inning = inningService.getCurrentInning(theGame);
        FinalResult finalResult = theGame.getFinalResult();
        if (runner.getTeam().getId() == theGame.getHomeTeam().getId()) {
            int currentRunsHomeTeamInInning = inning.getHomeTeamRuns();
            inning.setHomeTeamRuns(currentRunsHomeTeamInInning + 1);
            logger.info("Runs of inning with id {} were increased with one. Home team.", inning.getId());
            int currentRunsHomeTeamInGame = finalResult.getHomeTeamScore();
            finalResult.setHomeTeamScore(currentRunsHomeTeamInGame + 1);
            logger.info("Runs of final_result with id {} were increased with one. Home team.", finalResult.getId());
        } else {
            int currentRunsVisitorTeamInInning = inning.getVisitorTeamRuns();
            inning.setVisitorTeamRuns(currentRunsVisitorTeamInInning + 1);
            logger.info("Runs of inning with id {} were increased with one. Visitor team.", inning.getId());
            int currentRunsVisitorTeamInGame = finalResult.getVisitorTeamScore();
            finalResult.setVisitorTeamScore(currentRunsVisitorTeamInGame + 1);
            logger.info("Runs of final_result with id {} were increased with one. Visitor team.", finalResult.getId());
        }

        inningService.save(inning);
        finalResultService.save(finalResult);
//        InningScoreCounterFacade.getScoreCounter().incrementRun();
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strike")
    public void strike(@PathVariable int gameId,
                       @PathVariable int pitcherId, @PathVariable int batterId) {
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);
        Game theGame = gameService.findById(gameId);
        int currentNumberOfStrikes = batter.getStrikeCount();
        currentNumberOfStrikes++;
        batter.setStrikeCount(currentNumberOfStrikes);
        playerService.save(batter);
        logger.info("Pitcher with id {} made a strike to batter with id {}", pitcherId, batterId);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strikeoutLooking")
    public void strikeoutLooking(@PathVariable int gameId,
                                 @PathVariable int pitcherId, @PathVariable int batterId) {

        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);
        Inning inning = inningService.getCurrentInning(theGame);

        updateHittingStatisticsStrikeOut(batter, theGame);
        updatePitchingStatisticsStrikeOut(pitcher, theGame);
        updateFieldingStatisticsOut(pitcher.getTeam(), theGame);

        batter.setOffencePosition(null);
        batter.setStrikeCount(0);
        playerService.save(batter);

        inning.setCurrentOuts(inning.getCurrentOuts() + 1);
        inningService.save(inning);

        if (inning.getCurrentOuts() == 3) {
            switchFields(theGame, inning, pitcher, batter);
            logger.info("Teams switched fields.");
        }

        setNextBatter(batter, theGame);
    }

    private void switchFields(Game theGame, Inning inning, Player pitcher, Player batter) {
        Team homeTeam = theGame.getHomeTeam();
        Team visitorTeam = theGame.getVisitorTeam();
        int currentInningNumber = inning.getInningNumber();

        //if the visitor team is defending, begin new inning
        if (pitcher.getTeam().getId() == visitorTeam.getId()) {
            Inning nextInning = new Inning();
            nextInning.setGame(theGame);
            nextInning.setInningNumber(currentInningNumber + 1);
            inningService.save(nextInning);
            logger.info("Created new inning.");
        } else {
            inning.setCurrentOuts(0);
            inningService.save(inning);
            logger.info("Set current outs to 0.");
        }
        resetOffensePosition(batter.getTeam());
        if ((inning.getInningNumber() == 1) && (pitcher.getTeam().getId() != visitorTeam.getId())) {
            initializeGameDetails(visitorTeam, homeTeam, theGame);
        }
    }

    private void resetOffensePosition(Team team) {
        List<Player> players = team.getPlayers();

        Player[] arrPlayers = new Player[players.size()];

        for (int i = 0; i < players.size(); i++) {
            arrPlayers[i] = players.get(i);
        }

        for (Player player : arrPlayers) {
            if (player.getOffencePosition() != null) {
                player.setOffencePosition(null);
                playerService.save(player);
            }
        }
        logger.info("Set offense position to null.");
    }

    private void updateHittingStatisticsStrikeOut(Player batter, Game theGame) {
        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();
        playerHittingDetails.setStrikeOut(playerHittingDetails.getStrikeOut() + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Increased strikeouts in player hitting statistics of batter with id {}", batter.getId());

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        gameHittingDetails.setStrikeOut(gameHittingDetails.getStrikeOut() + 1);
        gameHittingDetailsService.save(gameHittingDetails);
        logger.info("Increased strikeouts in game hitting statistics of batter with id {}", batter.getId());
    }

    private void updatePitchingStatisticsStrikeOut(Player pitcher, Game theGame) {
        //increase innings pitched
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        double inningsPitchedAll = playerPitchingDetails.getInningsPitched();
        double newInningsPithedAll = increaseInningsPlayed(inningsPitchedAll);
        playerPitchingDetails.setInningsPitched(newInningsPithedAll);
        logger.info("Player pitching details: Increased innings pitched of pitcher with id {} from {} to {}",
                pitcher.getId(), inningsPitchedAll, newInningsPithedAll);
        //increase strikeouts
        playerPitchingDetails.setStrikeOuts(playerPitchingDetails.getStrikeOuts() + 1);
        logger.info("Player pitching details: Increased strikeouts of pitcher with id {}.", pitcher.getId());
        playerPitchingDetailsService.save(playerPitchingDetails);

        //increase innings pitched
        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        double inningsPitchedGame = gamePitchingDetails.getInningsPitched();
        double newInningsPitchedGame = increaseInningsPlayed(inningsPitchedGame);
        gamePitchingDetails.setInningsPitched(newInningsPitchedGame);
        logger.info("Game pitching details: Increased innings pitched of pitcher with id {} from {} to {}",
                pitcher.getId(), inningsPitchedGame, newInningsPitchedGame);
        gamePitchingDetails.setStrikeOuts(gamePitchingDetails.getStrikeOuts() + 1);
        logger.info("Game pitching details: Increased strikeouts of pitcher with id {}.", pitcher.getId());
        gamePitchingDetailsService.save(gamePitchingDetails);

    }

    private double increaseInningsPlayed(double inningsPitchedAll) {
        int decimal = (int) inningsPitchedAll;
        int fractionalPart = (int) Math.round((inningsPitchedAll - decimal) * 10);
        if ((fractionalPart == 0) || (fractionalPart == 1)) {
            return inningsPitchedAll + 0.1;
        } else {
            return decimal + 1;
        }
    }


    private void updateFieldingStatisticsOut(Team team, Game theGame) {
        List<Player> fielders = team.getPlayers();

        Player[] arrFielders = new Player[fielders.size()];

        for (int i = 0; i < fielders.size(); i++) {
            arrFielders[i] = fielders.get(i);
        }

        for (Player fielder : arrFielders) {
            if (fielder.getDefencePosition() != null) {
                PlayerFieldingDetails playerFieldingDetails = fielder.getPlayerFieldingDetails();
                double inningsAll = playerFieldingDetails.getInnings();
                double newInningsAll = increaseInningsPlayed(inningsAll);
                playerFieldingDetails.setInnings(newInningsAll);
                playerFieldingDetailsService.save(playerFieldingDetails);

                GameFieldingDetails gameFieldingDetails =
                        gameFieldingDetailsService.getGameFieldingDetails(fielder, theGame);
                double inningsGame = gameFieldingDetails.getInnings();
                double newInningsGame = increaseInningsPlayed(inningsGame);
                gameFieldingDetails.setInnings(newInningsGame);
                gameFieldingDetailsService.save(gameFieldingDetails);
            }
        }
        logger.info("Increased innings played of fielders.");

    }


    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strikeoutSwinging")
    public void strikeoutSwinging(@PathVariable int gameId,
                                  @PathVariable int pitcherId, @PathVariable int batterId) {

    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/caughtFoulTip")
    public void caughtFoulTip(@PathVariable int gameId,
                              @PathVariable int pitcherId, @PathVariable int batterId) {

    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/buntFoul")
    public void buntFoul(@PathVariable int gameId,
                         @PathVariable int pitcherId, @PathVariable int batterId) {

    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/dropped3rdStrike")
    public void dropped3rdStrike(@PathVariable int gameId,
                                 @PathVariable int pitcherId, @PathVariable int batterId) {

    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/wildPitch3rdStrike")
    public void wildPitch3rdStrike(@PathVariable int gameId,
                                   @PathVariable int pitcherId, @PathVariable int batterId) {

    }

    private void setNextBatter(Player currentBatter, Game theGame) {
        Player nextBatter = playerService.getNextBatter(currentBatter);
        initializeGameHittingDetails(nextBatter, theGame);
        logger.info("Batter with id {} entered the game.", nextBatter.getId());
    }

    @PutMapping("/games/{gameId}/{batterId}/foul")
    public void foul(@PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/out")
    public void strikeOut(@PathVariable int gameId,
                          @PathVariable int pitcherId, @PathVariable int batterId) {

//        InningScoreCounterFacade.getScoreCounter().incrementOut();
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
