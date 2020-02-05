package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.Constants;
import com.junak.scorekeeper.dto.GameDto;
import com.junak.scorekeeper.entity.*;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.*;
import com.junak.scorekeeper.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private PlayService playService;

    @Autowired
    public GameRestController(GameService gameService, PlayerService playerService,
                              TeamService teamService, InningService inningService,
                              FinalResultService finalResultService,
                              GameFieldingDetailsService gameFieldingDetailsService,
                              GameHittingDetailsService gameHittingDetailsService,
                              GamePitchingDetailsService gamePitchingDetailsService,
                              PlayerFieldingDetailsService playerFieldingDetailsService,
                              PlayerHittingDetailsService playerHittingDetailsService,
                              PlayerPitchingDetailsService playerPitchingDetailsService,
                              PlayService playService) {

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
        this.playService = playService;
    }

    @GetMapping("/games")
    public List<GameDto> findAll() {
        List<Game> games = gameService.findAll();
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game : games) {
            gameDtos.add(convertToDto(game));
        }
        return gameDtos;
    }

    @GetMapping("/games/{gameId}")
    public GameDto getGame(@PathVariable int gameId) {

        Game theGame = gameService.findById(gameId);

        if (theGame == null) {
            throw new GameNotFoundException("Game id not found - " + gameId);
        }

        return convertToDto(theGame);
    }

    @PostMapping("/games")
    public Game addGame(@RequestBody GameDto gameDto) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        gameDto.setId(0);

        Game theGame = gameService.save(convertToEntity(gameDto));

        return theGame;
    }

    @PutMapping("/games")
    public Game updateGame(@RequestBody GameDto gameDto) {

        Game theGame = convertToEntity(gameDto);
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

        List<GameHittingDetails> gameHittingDetailsList =
                gameHittingDetailsService.getGameHittingDetailsList(tempGame);
        List<GamePitchingDetails> gamePitchingDetailsList =
                gamePitchingDetailsService.getGamePitchingDetailsList(tempGame);
        List<GameFieldingDetails> gameFieldingDetailsList =
                gameFieldingDetailsService.getGameFieldingDetailsList(tempGame);
        List<Inning> inningList = inningService.getInningsList(tempGame);

        tempGame.setGameHittingDetails(null);
        tempGame.setGamePitchingDetails(null);
        tempGame.setGameFieldingDetails(null);
        tempGame.setInnings(null);
        gameService.save(tempGame);

        if ((gameHittingDetailsList != null) && (gameHittingDetailsList.size() > 0)) {
            GameHittingDetails[] hittingDetailsToDeleteArr = new GameHittingDetails[gameHittingDetailsList.size()];

            for (int i = 0; i < hittingDetailsToDeleteArr.length; i++) {
                GameHittingDetails hittingDetailsToDelete = gameHittingDetailsList.get(i);
                gameHittingDetailsService.deleteById(hittingDetailsToDelete.getId());
            }
        }

        if ((gamePitchingDetailsList != null) && (gamePitchingDetailsList.size() > 0)) {
            GamePitchingDetails[] pitchingDetailsToDeleteArr = new GamePitchingDetails[gamePitchingDetailsList.size()];

            for (int i = 0; i < pitchingDetailsToDeleteArr.length; i++) {
                GamePitchingDetails pitchingDetailsToDelete = gamePitchingDetailsList.get(i);
                gamePitchingDetailsService.deleteById(pitchingDetailsToDelete.getId());
            }
        }

        if ((gameFieldingDetailsList != null) && (gameFieldingDetailsList.size() > 0)) {
            GameFieldingDetails[] fieldingDetailsToUpdateArr = new GameFieldingDetails[gameFieldingDetailsList.size()];

            for (int i = 0; i < fieldingDetailsToUpdateArr.length; i++) {
                GameFieldingDetails fieldingDetailsToUpdate = gameFieldingDetailsList.get(i);
                gameFieldingDetailsService.deleteById(fieldingDetailsToUpdate.getId());
            }
        }

        if ((inningList != null) && (inningList.isEmpty())) {
            Inning[] inningsToDeleteArr = new Inning[inningList.size()];

            for (int i = 0; i < inningsToDeleteArr.length; i++) {
                Inning inningToDelete = inningList.get(i);
                inningService.deleteById(inningToDelete.getId());
            }
        }

        if (tempGame.getFinalResult() != null) {
            finalResultService.deleteById(tempGame.getFinalResult().getId());
        }

        gameService.deleteById(gameId);

        return "Deleted game id - " + gameId;
        //TODO player details don't change when deleting game
    }

    @PostMapping("/games/{homeTeamId}/{visitorTeamId}/createNewGame")
    public void createNewGame(@PathVariable int homeTeamId, @PathVariable int visitorTeamId) {
        Team homeTeam = teamService.findById(homeTeamId);
        Team visitorTeam = teamService.findById(visitorTeamId);
        homeTeam.setAttacking(false);
        visitorTeam.setAttacking(true);
        teamService.save(homeTeam);
        teamService.save(visitorTeam);

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

    @PutMapping("/games/{gameId}/endGame")
    public void endGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        Date currentDate = new Date();
        theGame.setEndTimeOfGame(currentDate);
        gameService.save(theGame);
        //TODO reset players offence positions, ball count, strike count, was pitcher
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
        Player pitcher = playerService.findById(pitcherId);
        Inning inning = inningService.getCurrentInning(theGame);
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

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);

        play.setAction(String.format("%s %s pitched outside the strike zone.", pitcher.getFirstName(), pitcher.getLastName()));
        playService.save(play);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/walk")
    public void walk(@PathVariable int gameId,
                     @PathVariable int pitcherId, @PathVariable int batterId) {
        
        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s walked to first base.", batter.getFirstName(), batter.getLastName()));
        playService.save(play);

        unintentionalWalk(gameId, pitcherId, batterId);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/intentionalWalk")
    public void intentionalWalk(@PathVariable int gameId,
                                @PathVariable int pitcherId, @PathVariable int batterId) {

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);
        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s walked to first base intentionally.", batter.getFirstName(), batter.getLastName()));
        playService.save(play);

        unintentionalWalk(gameId, pitcherId, batterId);
    }

    private void unintentionalWalk(int gameId, int pitcherId, int batterId) {
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
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strike")
    public void strike(@PathVariable int gameId, @PathVariable int pitcherId, @PathVariable int batterId) {
        Player batter = playerService.findById(batterId);
        Player pitcher = playerService.findById(pitcherId);
        int currentNumberOfStrikes = batter.getStrikeCount();
        currentNumberOfStrikes++;
        batter.setStrikeCount(currentNumberOfStrikes);
        playerService.save(batter);
        logger.info("Pitcher with id {} made a strike to batter with id {}", pitcherId, batterId);

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s made a strike to %s %s.", pitcher.getFirstName(), pitcher.getLastName(),
                batter.getFirstName(), batter.getLastName()));
        playService.save(play);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strikeoutLooking")
    public void strikeoutLooking(@PathVariable int gameId,
                                 @PathVariable int pitcherId, @PathVariable int batterId) {

        strikeout(gameId, pitcherId, batterId);

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);
        Player pitcher = playerService.findById(pitcherId);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s made a strikeout to %s %s while looking.", pitcher.getFirstName(), pitcher.getLastName(),
                batter.getFirstName(), batter.getLastName()));
        playService.save(play);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/strikeoutSwinging")
    public void strikeoutSwinging(@PathVariable int gameId,
                                  @PathVariable int pitcherId, @PathVariable int batterId) {

        strikeout(gameId, pitcherId, batterId);

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);
        Player pitcher = playerService.findById(pitcherId);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s made a strikeout to %s %s while swinging.", pitcher.getFirstName(), pitcher.getLastName(),
                batter.getFirstName(), batter.getLastName()));
        playService.save(play);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/caughtFoulTip")
    public void caughtFoulTip(@PathVariable int gameId,
                              @PathVariable int pitcherId, @PathVariable int batterId) {

        strikeout(gameId, pitcherId, batterId);

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);
        Player pitcher = playerService.findById(pitcherId);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s made a foul tip to %s %s.", pitcher.getFirstName(), pitcher.getLastName(),
                batter.getFirstName(), batter.getLastName()));
        playService.save(play);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/buntFoul")
    public void buntFoul(@PathVariable int gameId,
                         @PathVariable int pitcherId, @PathVariable int batterId) {

        strikeout(gameId, pitcherId, batterId);

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);
        Player pitcher = playerService.findById(pitcherId);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s made a foul bunt to %s %s.", pitcher.getFirstName(), pitcher.getLastName(),
                batter.getFirstName(), batter.getLastName()));
        playService.save(play);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/out/dropped3rdStrike")
    public void dropped3rdStrikeOut(@PathVariable int gameId,
                                    @PathVariable int pitcherId, @PathVariable int batterId) {

        //TODO first I have to implement "What happened to this player?"
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/safe/dropped3rdStrike")
    public void dropped3rdStrikeSafe(@PathVariable int gameId,
                                     @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO first I have to implement "What happened to this player?"
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/wildPitch3rdStrike")
    public void wildPitch3rdStrike(@PathVariable int gameId,
                                   @PathVariable int pitcherId, @PathVariable int batterId) {
        //TODO first I have to implement "What happened to this player?"
    }

    @PutMapping("/games/{gameId}/{batterId}/foul")
    public void foul(@PathVariable int gameId, @PathVariable int batterId) {

        Game theGame = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(theGame);
        Player batter = playerService.findById(batterId);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s made a foul.", batter.getFirstName(), batter.getLastName()));
        playService.save(play);
    }

//    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/out")
//    public void strikeOut(@PathVariable int gameId,
//                          @PathVariable int pitcherId, @PathVariable int batterId) {
//
////        InningScoreCounterFacade.getScoreCounter().incrementOut();
//    }

    @PutMapping("/games/{gameId}/{pitcherId}/{runnerId}/{catcherId}/{basemanId}/caughtStealing")
    public void caughtStealing(@PathVariable int gameId, @PathVariable int pitcherId, @PathVariable int runnerId,
                               @PathVariable int catcherId, @PathVariable int basemanId) {
        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player runner = playerService.findById(runnerId);
        Player catcher = playerService.findById(catcherId);
        Player baseman = playerService.findById(basemanId);
        Inning inning = inningService.getCurrentInning(theGame);

        //increase caught stealing CS of hitting details
        PlayerHittingDetails playerHittingDetails = runner.getPlayerHittingDetails();
        playerHittingDetails.setCaughtStealing(playerHittingDetails.getCaughtStealing() + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Player Hitting Details: Increased caught stealing CS of runner with id {}", runnerId);

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(runner, theGame);
        gameHittingDetails.setCaughtStealing(gameHittingDetails.getCaughtStealing() + 1);
        gameHittingDetailsService.save(gameHittingDetails);
        logger.info("Game Hitting Details: Increased caught stealing CS of runner with id {}", runnerId);

        //increase assists A of catcher
        increaseAssists(catcher, theGame);

        //increase putout of fielder
        increasePutout(baseman, theGame);

        //increase innings pitched
        increaseInningsPitched(pitcher, theGame);

        runner.setOffensePosition(null);
        playerService.save(runner);

        inning.setCurrentOuts(inning.getCurrentOuts() + 1);
        inningService.save(inning);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s was caught stealing by %s %s.", runner.getFirstName(), runner.getLastName(),
                baseman.getFirstName(), baseman.getLastName()));
        playService.save(play);

        if (inning.getCurrentOuts() == 3) {
            switchFields(theGame, inning, pitcher, runner);
            logger.info("Teams switched fields.");
        }
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{runnerId}/{baseStolen}/stolenBase")
    public void stolenBase(@PathVariable int gameId, @PathVariable int pitcherId, @PathVariable int runnerId,
                           @PathVariable String baseStolen) {
        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player runner = playerService.findById(runnerId);

        PlayerHittingDetails playerHittingDetailsRunner = runner.getPlayerHittingDetails();
        playerHittingDetailsRunner.setStolenBase(playerHittingDetailsRunner.getStolenBase() + 1);
        playerHittingDetailsService.save(playerHittingDetailsRunner);
        logger.info("Player Hitting Details: Increased stolen base of runner with id {}.", runnerId);

        GameHittingDetails gameHittingDetailsRunner = gameHittingDetailsService.getGameHittingDetails(runner, theGame);
        gameHittingDetailsRunner.setStolenBase(gameHittingDetailsRunner.getStolenBase() + 1);
        gameHittingDetailsService.save(gameHittingDetailsRunner);
        logger.info("Game Hitting Details: Increased stolen base of runner with id {}.", runnerId);

        Inning inning = inningService.getCurrentInning(theGame);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s stole %s.", runner.getFirstName(), runner.getLastName(), baseStolen));
        playService.save(play);

        runner.setOffensePosition(baseStolen);
        if (baseStolen.equals(Constants.runnerHomeBase)) {
            scoreARun(runner, null, pitcher, theGame, false, true);
            runner.setOffensePosition(null);
        }

        playerService.save(runner);
        logger.info("Runner with id {} stole {}.", runnerId, baseStolen);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/hitSingle")
    public void hitSingle(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);

        //increase hits to first base
        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();
        playerHittingDetails.setHits(playerHittingDetails.getHits() + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Player Hitting Details: Increased single hits of batter with id {}.", batterId);

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        gameHittingDetails.setHits(gameHittingDetails.getHits() + 1);
        gameHittingDetailsService.save(gameHittingDetails);
        logger.info("Game Hitting Details: Increased single hits of batter with id {} in game with id {}.",
                batterId, gameId);

        //increase hits allowed by pitcher
        increaseHitsAllowedByPitcher(pitcher, theGame);

        //increase hits of team
        increaseHitsOfTeam(theGame, batter);

        batter.setOffensePosition(Constants.runnerFirstBase);
        playerService.save(batter);
        logger.info("Batter with id {} moved to first base.", batterId);

        Inning inning = inningService.getCurrentInning(theGame);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s hit single.", batter.getFirstName(), batter.getLastName()));
        playService.save(play);

        setNextBatter(batter, theGame);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/hitDouble")
    public void hitDouble(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {
        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);

        //increase hits to second base
        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();
        playerHittingDetails.setDoubleHit(playerHittingDetails.getDoubleHit() + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Player Hitting Details: Increased double hits of batter with id {}.", batterId);

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        gameHittingDetails.setDoubleHit(gameHittingDetails.getDoubleHit() + 1);
        gameHittingDetailsService.save(gameHittingDetails);
        logger.info("Game Hitting Details: Increased double hits of batter with id {} in game with id {}.",
                batterId, gameId);

        //increase hits allowed by pitcher
        increaseHitsAllowedByPitcher(pitcher, theGame);

        //increase hits of team
        increaseHitsOfTeam(theGame, batter);

        batter.setOffensePosition(Constants.runnerSecondBase);
        playerService.save(batter);
        logger.info("Batter with id {} moved to second base.", batterId);

        Inning inning = inningService.getCurrentInning(theGame);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s hit double.", batter.getFirstName(), batter.getLastName()));
        playService.save(play);

        setNextBatter(batter, theGame);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/hitTriple")
    public void hitTriple(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {
        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);

        //increase hits to third base
        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();
        playerHittingDetails.setTripleHit(playerHittingDetails.getTripleHit() + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Player Hitting Details: Increased triple hits of batter with id {}.", batterId);

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        gameHittingDetails.setTripleHit(gameHittingDetails.getTripleHit() + 1);
        gameHittingDetailsService.save(gameHittingDetails);
        logger.info("Game Hitting Details: Increased triple hits of batter with id {} in game with id {}.",
                batterId, gameId);

        //increase hits allowed by pitcher
        increaseHitsAllowedByPitcher(pitcher, theGame);

        //increase hits of team
        increaseHitsOfTeam(theGame, batter);

        batter.setOffensePosition(Constants.runnerThirdBase);
        playerService.save(batter);
        logger.info("Batter with id {} moved to third base.", batterId);

        Inning inning = inningService.getCurrentInning(theGame);

        Play play = new Play();
        play.setInning(inning);
        play.setGame(theGame);
        play.setAction(String.format("%s %s hit triple.", batter.getFirstName(), batter.getLastName()));
        playService.save(play);

        setNextBatter(batter, theGame);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/homeRun")
    public void homeRun(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {
        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);

        //increase home runs
        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();
        playerHittingDetails.setHomeRun(playerHittingDetails.getHomeRun() + 1);
        playerHittingDetailsService.save(playerHittingDetails);
        logger.info("Player Hitting Details: Increased home runs of batter with id {}.", batterId);

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(batter, theGame);
        gameHittingDetails.setHomeRun(gameHittingDetails.getHomeRun() + 1);
        gameHittingDetailsService.save(gameHittingDetails);
        logger.info("Game Hitting Details: Increased home runs of batter with id {} in game with id {}.",
                batterId, gameId);

        //increase hits allowed by pitcher
        increaseHitsAllowedByPitcher(pitcher, theGame);

        //increase home runs allowed by pitcher
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        playerPitchingDetails.setHomeRuns(playerPitchingDetails.getHomeRuns() + 1);
        playerPitchingDetailsService.save(playerPitchingDetails);
        logger.info("Player Pitching Details: Increase home runs allowed by pitcher with id {}", pitcherId);

        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        gamePitchingDetails.setHomeRuns(gamePitchingDetails.getHomeRuns() + 1);
        gamePitchingDetailsService.save(gamePitchingDetails);
        logger.info("Game Pitching Details: Increase home runs allowed by pitcher with id {} in game with id {}",
                pitcherId, gameId);

        //increase hits of team
        increaseHitsOfTeam(theGame, batter);

        Inning inning = inningService.getCurrentInning(theGame);

        Play batterPlay = new Play();
        batterPlay.setInning(inning);
        batterPlay.setGame(theGame);
        batterPlay.setAction(String.format("%s %s made a home run.", batter.getFirstName(), batter.getLastName()));
        playService.save(batterPlay);

        scoreARun(batter, batter, pitcher, theGame, true, true);

        Player firstBaseRunner = playerService.getFirstBaseRunner(batter.getTeam());
        Player secondBaseRunner = playerService.getSecondBaseRunner(batter.getTeam());
        Player thirdBaseRunner = playerService.getThirdBaseRunner(batter.getTeam());

        if (firstBaseRunner != null) {
            Play firstBaseRunnerPlay = new Play();
            firstBaseRunnerPlay.setInning(inning);
            firstBaseRunnerPlay.setGame(theGame);
            firstBaseRunnerPlay.setAction(String.format("%s %s reached home base.", firstBaseRunner.getFirstName(),
                    firstBaseRunner.getLastName()));
            playService.save(firstBaseRunnerPlay);

            scoreARun(firstBaseRunner, batter, pitcher, theGame, true, true);
            firstBaseRunner.setOffensePosition(null);
            playerService.save(firstBaseRunner);
        }
        if (secondBaseRunner != null) {
            Play secondBaseRunnerPlay = new Play();
            secondBaseRunnerPlay.setInning(inning);
            secondBaseRunnerPlay.setGame(theGame);
            secondBaseRunnerPlay.setAction(String.format("%s %s reached home base.", secondBaseRunner.getFirstName(),
                    secondBaseRunner.getLastName()));
            playService.save(secondBaseRunnerPlay);

            scoreARun(secondBaseRunner, batter, pitcher, theGame, true, true);
            secondBaseRunner.setOffensePosition(null);
            playerService.save(secondBaseRunner);
        }
        if (thirdBaseRunner != null) {
            Play thirdBaseRunnerPlay = new Play();
            thirdBaseRunnerPlay.setInning(inning);
            thirdBaseRunnerPlay.setGame(theGame);
            thirdBaseRunnerPlay.setAction(String.format("%s %s reached home base.", thirdBaseRunner.getFirstName(),
                    thirdBaseRunner.getLastName()));
            playService.save(thirdBaseRunnerPlay);

            scoreARun(thirdBaseRunner, batter, pitcher, theGame, true, true);
            thirdBaseRunner.setOffensePosition(null);
            playerService.save(thirdBaseRunner);
        }

        batter.setOffensePosition(null);
        playerService.save(batter);
        logger.info("Batter with id {} moved to home base.", batterId);

        setNextBatter(batter, theGame);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/inParkHomeRun")
    public void inParkHomeRun(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {
        homeRun(pitcherId, gameId, batterId);
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/safe/bunt")
    public void buntSafe(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/error")
    public void error(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/hitByPitch")
    public void hitByPitch(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/fieldersChoice")
    public void fieldersChoice(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/groundOut")
    public void groundOut(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/lineDrive")
    public void lineDrive(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/flyOut")
    public void flyOut(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/out/bunt")
    public void buntOut(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/sacrificeFly")
    public void sacrificeFly(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/sacrificeBunt")
    public void sacrificeBunt(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/infieldFly")
    public void infieldFly(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/hitByBall")
    public void hitByBall(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/runnerInterference")
    public void runnerInterference(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    @PutMapping("/games/{gameId}/{pitcherId}/{batterId}/offensiveInterference")
    public void offensiveInterference(@PathVariable int pitcherId, @PathVariable int gameId, @PathVariable int batterId) {

        //TODO
    }

    private void increaseHitsOfTeam(Game game, Player batter) {
        FinalResult finalResult = game.getFinalResult();
        if (game.getHomeTeam().getId() == batter.getTeam().getId()) {
            finalResult.setHomeTeamHits(finalResult.getHomeTeamHits() + 1);
        } else {
            finalResult.setVisitorTeamHits(finalResult.getVisitorTeamHits() + 1);
        }
    }

    private void initializeGameDetails(Team defendingTeam, Team offensiveTeam, Game theGame) {
        Player batter = playerService.getStartingBatter(offensiveTeam);
        initializeGameHittingDetails(batter, theGame);
        initializeGamePitchingDetails(defendingTeam, theGame);
        initializeGameFieldingDetails(defendingTeam, theGame);
    }

    private void initializeGameHittingDetails(Player batter, Game theGame) {
        batter.setOffensePosition(Constants.batter);
        playerService.save(batter);

        Inning inning = inningService.getCurrentInning(theGame);
        Play batterPlay = new Play();
        batterPlay.setInning(inning);
        batterPlay.setGame(theGame);
        batterPlay.setAction(String.format("%s %s is at bat.", batter.getFirstName(),
                batter.getLastName()));
        playService.save(batterPlay);

        PlayerHittingDetails playerHittingDetails = batter.getPlayerHittingDetails();

        if (gameHittingDetailsService.getGameHittingDetails(batter, theGame) == null) {
            GameHittingDetails gameHittingDetails = new GameHittingDetails();
            gameHittingDetails.setPlayer(batter);
            gameHittingDetails.setGame(theGame);
            gameHittingDetails.setPlateAppearances(1);
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

        pitcher.setWasPitcher(true);
        playerService.save(pitcher);

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

    private void pushRunnersWithOneBase(Player batter, Player pitcher, Team offensiveTeam, Game theGame) {
        Player firstBaseRunner = playerService.getFirstBaseRunner(offensiveTeam);
        Player secondBaseRunner = playerService.getSecondBaseRunner(offensiveTeam);
        Player thirdBaseRunner = playerService.getThirdBaseRunner(offensiveTeam);
        Inning inning = inningService.getCurrentInning(theGame);

        batter.setOffensePosition(Constants.runnerFirstBase);
        playerService.save(batter);

        setNextBatter(batter, theGame);

        if (firstBaseRunner != null) {
            firstBaseRunner.setOffensePosition(Constants.runnerSecondBase);
            playerService.save(firstBaseRunner);
            logger.info("Batter with id {} pushed player with id {} from first base to second base.",
                    batter.getId(), firstBaseRunner.getId());

            Play firstBaseRunnerPlay = new Play();
            firstBaseRunnerPlay.setInning(inning);
            firstBaseRunnerPlay.setGame(theGame);
            firstBaseRunnerPlay.setAction(String.format("%s %s pushed %s %s from first base to second base.",
                    batter.getFirstName(), batter.getLastName(),
                    firstBaseRunner.getFirstName(), firstBaseRunner.getLastName()));
            playService.save(firstBaseRunnerPlay);

            if (secondBaseRunner != null) {
                secondBaseRunner.setOffensePosition(Constants.runnerThirdBase);
                playerService.save(secondBaseRunner);
                logger.info("Runner from first base with id {} pushed runner with id {} from second to third base.",
                        firstBaseRunner.getId(), secondBaseRunner.getId());

                Play secondBaseRunnerPlay = new Play();
                secondBaseRunnerPlay.setInning(inning);
                secondBaseRunnerPlay.setGame(theGame);
                secondBaseRunnerPlay.setAction(String.format("%s %s pushed %s %s from second base to third base.",
                        firstBaseRunner.getFirstName(), firstBaseRunner.getLastName(),
                        secondBaseRunner.getFirstName(), secondBaseRunner.getLastName()));
                playService.save(secondBaseRunnerPlay);

                if (thirdBaseRunner != null) {
                    thirdBaseRunner.setOffensePosition(Constants.runnerHomeBase);
                    playerService.save(thirdBaseRunner);
                    logger.info("Runner from second base with id {} pushed runner with id {} from third to home base.",
                            secondBaseRunner.getId(), thirdBaseRunner.getId());

                    Play thirdBaseRunnerPlay = new Play();
                    thirdBaseRunnerPlay.setInning(inning);
                    thirdBaseRunnerPlay.setGame(theGame);
                    thirdBaseRunnerPlay.setAction(String.format("%s %s pushed %s %s from third base to home base.",
                            secondBaseRunner.getFirstName(), secondBaseRunner.getLastName(),
                            thirdBaseRunner.getFirstName(), thirdBaseRunner.getLastName()));
                    playService.save(thirdBaseRunnerPlay);

                    scoreARun(thirdBaseRunner, batter, pitcher, theGame, true, true);
                }
            }
        }
    }

    private void scoreARun(Player runner, Player batter, Player pitcher, Game theGame,
                           boolean withBatterHelp, boolean isEarned) {
        //increase player runs of runner
        PlayerHittingDetails PlayerHittingDetailsRunner = runner.getPlayerHittingDetails();
        PlayerHittingDetailsRunner.setRuns(PlayerHittingDetailsRunner.getRuns() + 1);
        playerHittingDetailsService.save(PlayerHittingDetailsRunner);
        logger.info("Increase player runs by runner with id {}.", runner.getId());

        //increase game runs of runner
        GameHittingDetails GameHittingDetailsRunner = gameHittingDetailsService.getGameHittingDetails(runner, theGame);
        GameHittingDetailsRunner.setRuns(GameHittingDetailsRunner.getRuns() + 1);
        gameHittingDetailsService.save(GameHittingDetailsRunner);
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

    private void strikeout(int gameId, int pitcherId, int batterId) {
        Game theGame = gameService.findById(gameId);
        Player pitcher = playerService.findById(pitcherId);
        Player batter = playerService.findById(batterId);
        Inning inning = inningService.getCurrentInning(theGame);

        updateHittingStatisticsStrikeOut(batter, theGame);
        updatePitchingStatisticsStrikeOut(pitcher, theGame);
        updateFieldingStatisticsOut(pitcher.getTeam(), theGame);

        batter.setOffensePosition(null);
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
            visitorTeam.setAttacking(true);
            homeTeam.setAttacking(false);
        } else {
            inning.setCurrentOuts(0);
            inningService.save(inning);
            logger.info("Set current outs to 0.");
            homeTeam.setAttacking(true);
            visitorTeam.setAttacking(false);
        }
        teamService.save(visitorTeam);
        teamService.save(homeTeam);
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
            if ((player.getOffensePosition() != null) && (!player.getOffensePosition().equals("batter"))) {
                player.setOffensePosition(null);
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
        increaseInningsPitched(pitcher, theGame);

        //increase strikeouts
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        playerPitchingDetails.setStrikeOuts(playerPitchingDetails.getStrikeOuts() + 1);
        logger.info("Player pitching details: Increased strikeouts of pitcher with id {}.", pitcher.getId());
        playerPitchingDetailsService.save(playerPitchingDetails);

        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        gamePitchingDetails.setStrikeOuts(gamePitchingDetails.getStrikeOuts() + 1);
        logger.info("Game pitching details: Increased strikeouts of pitcher with id {}.", pitcher.getId());
        gamePitchingDetailsService.save(gamePitchingDetails);
    }

    private void increaseInningsPitched(Player pitcher, Game theGame) {
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        double inningsPitchedAll = playerPitchingDetails.getInningsPitched();
        double newInningsPitchedAll = increaseInningsPlayed(inningsPitchedAll);
        playerPitchingDetails.setInningsPitched(newInningsPitchedAll);
        playerPitchingDetailsService.save(playerPitchingDetails);
        logger.info("Player pitching details: Increased innings pitched of pitcher with id {} from {} to {}",
                pitcher.getId(), inningsPitchedAll, newInningsPitchedAll);

        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        double inningsPitchedGame = gamePitchingDetails.getInningsPitched();
        double newInningsPitchedGame = increaseInningsPlayed(inningsPitchedGame);
        gamePitchingDetails.setInningsPitched(newInningsPitchedGame);
        gamePitchingDetailsService.save(gamePitchingDetails);
        logger.info("Game pitching details: Increased innings pitched of pitcher with id {} from {} to {}",
                pitcher.getId(), inningsPitchedGame, newInningsPitchedGame);
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

    private void setNextBatter(Player currentBatter, Game theGame) {
        Player nextBatter = playerService.getNextBatter(currentBatter);
        initializeGameHittingDetails(nextBatter, theGame);
        logger.info("Batter with id {} entered the game.", nextBatter.getId());
    }

    private void increaseAssists(Player fielder, Game theGame) {
        PlayerFieldingDetails playerFieldingDetails = fielder.getPlayerFieldingDetails();
        playerFieldingDetails.setAssists(playerFieldingDetails.getAssists() + 1);
        playerFieldingDetailsService.save(playerFieldingDetails);
        logger.info("Player Fielding Details: Increased Assists A of fielder with id {} in game with id {}",
                fielder.getId(), theGame.getId());

        GameFieldingDetails gameFieldingDetails =
                gameFieldingDetailsService.getGameFieldingDetails(fielder, theGame);
        gameFieldingDetails.setAssists(gameFieldingDetails.getAssists() + 1);
        gameFieldingDetailsService.save(gameFieldingDetails);
        logger.info("Game Fielding Details: Increased Assists A of fielder with id {} in game with id {}",
                fielder.getId(), theGame.getId());
    }

    private void increasePutout(Player baseman, Game theGame) {
        PlayerFieldingDetails playerFieldingDetailsBaseman = baseman.getPlayerFieldingDetails();
        playerFieldingDetailsBaseman.setPutOut(playerFieldingDetailsBaseman.getPutOut() + 1);
        playerFieldingDetailsService.save(playerFieldingDetailsBaseman);
        logger.info("Player Fielding Details: Increased putouts PO of player with id {}", baseman.getId());

        GameFieldingDetails gameFieldingDetailsBaseman =
                gameFieldingDetailsService.getGameFieldingDetails(baseman, theGame);
        gameFieldingDetailsBaseman.setPutOut(gameFieldingDetailsBaseman.getPutOut() + 1);
        gameFieldingDetailsService.save(gameFieldingDetailsBaseman);
        logger.info("Game Fielding Details: Increased putouts PO of player with id {} in game with id {}",
                baseman.getId(), theGame.getId());
    }

    private void increaseHitsAllowedByPitcher(Player pitcher, Game theGame) {
        PlayerPitchingDetails playerPitchingDetails = pitcher.getPlayerPitchingDetails();
        playerPitchingDetails.setHits(playerPitchingDetails.getHits() + 1);
        playerPitchingDetailsService.save(playerPitchingDetails);

        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(pitcher, theGame);
        gamePitchingDetails.setHits(gamePitchingDetails.getHits() + 1);
        gamePitchingDetailsService.save(gamePitchingDetails);
    }

    private GameDto convertToDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setScheduledTime(game.getScheduledTime());
        gameDto.setStartTimeOfGame(game.getStartTimeOfGame());
        gameDto.setEndTimeOfGame(game.getEndTimeOfGame());
        if (game.getHomeTeam() != null) {
            gameDto.setHomeTeam(game.getHomeTeam().getId());
        }
        if (game.getVisitorTeam() != null) {
            gameDto.setVisitorTeam(game.getVisitorTeam().getId());
        }
        if (game.getFinalResult() != null) {
            gameDto.setFinalResult(game.getFinalResult().getId());
        }
        if ((game.getGameHittingDetails() != null) && (game.getGameHittingDetails().size() != 0)) {
            List<Integer> gameHittingDetailsDto = new ArrayList<>();
            List<GameHittingDetails> gameHittingDetailsList = game.getGameHittingDetails();
            for (GameHittingDetails details : gameHittingDetailsList) {
                gameHittingDetailsDto.add(details.getId());
            }
            gameDto.setGameHittingDetails(gameHittingDetailsDto);
        }
        if ((game.getGamePitchingDetails() != null) && (game.getGamePitchingDetails().size() != 0)) {
            List<Integer> gamePitchingDetailsDto = new ArrayList<>();
            List<GamePitchingDetails> gamePitchingDetailsList = game.getGamePitchingDetails();
            for (GamePitchingDetails details : gamePitchingDetailsList) {
                gamePitchingDetailsDto.add(details.getId());
            }
            gameDto.setGamePitchingDetails(gamePitchingDetailsDto);
        }
        if ((game.getGameFieldingDetails() != null) && (game.getGameFieldingDetails().size() != 0)) {
            List<Integer> gameFieldingDetailsDto = new ArrayList<>();
            List<GameFieldingDetails> gameFieldingDetailsList = game.getGameFieldingDetails();
            for (GameFieldingDetails details : gameFieldingDetailsList) {
                gameFieldingDetailsDto.add(details.getId());
            }
            gameDto.setGameFieldingDetails(gameFieldingDetailsDto);
        }
        if (game.getWinPitcher() != null) {
            gameDto.setWinPitcher(game.getWinPitcher().getId());
        }
        if (game.getLosePitcher() != null) {
            gameDto.setLosePitcher(game.getLosePitcher().getId());
        }
        if (game.getSavePitcher() != null) {
            gameDto.setSavePitcher(game.getSavePitcher().getId());
        }
        if (game.getBlownSavePitcher() != null) {
            gameDto.setBlownSavePitcher(game.getBlownSavePitcher().getId());
        }
        if (game.getHoldPitcher() != null) {
            gameDto.setHoldPitcher(game.getHoldPitcher().getId());
        }
        if ((game.getInnings() != null) && (game.getInnings().size() != 0)) {
            List<Integer> inningsDto = new ArrayList<>();
            List<Inning> inningList = game.getInnings();
            for (Inning innings : inningList) {
                inningsDto.add(innings.getId());
            }
            gameDto.setInnings(inningsDto);
        }
        return gameDto;
    }

    private Game convertToEntity(GameDto gameDto) {
        Game game = new Game();

        if (gameDto.getId() != 0) {
            game = gameService.findById(gameDto.getId());
        }

        game.setScheduledTime(gameDto.getScheduledTime());
        game.setStartTimeOfGame(gameDto.getStartTimeOfGame());
        game.setEndTimeOfGame(gameDto.getEndTimeOfGame());
        if (gameDto.getHomeTeam() != 0) {
            game.setHomeTeam(teamService.findById(gameDto.getHomeTeam()));
        }
        if (gameDto.getVisitorTeam() != 0) {
            game.setVisitorTeam(teamService.findById(gameDto.getVisitorTeam()));
        }
        if (gameDto.getFinalResult() != 0) {
            game.setFinalResult(finalResultService.findById(gameDto.getFinalResult()));
        }
        if (gameDto.getWinPitcher() != 0) {
            game.setWinPitcher(playerService.findById(gameDto.getWinPitcher()));
        }
        if (gameDto.getLosePitcher() != 0) {
            game.setLosePitcher(playerService.findById(gameDto.getLosePitcher()));
        }
        if (gameDto.getSavePitcher() != 0) {
            game.setSavePitcher(playerService.findById(gameDto.getSavePitcher()));
        }
        if (gameDto.getBlownSavePitcher() != 0) {
            game.setBlownSavePitcher(playerService.findById(gameDto.getBlownSavePitcher()));
        }
        if (gameDto.getHoldPitcher() != 0) {
            game.setHoldPitcher(playerService.findById(gameDto.getHoldPitcher()));
        }
        if ((gameDto.getInnings() != null) && (gameDto.getInnings().size() != 0)) {
            List<Integer> inningsDto = gameDto.getInnings();
            List<Inning> inningList = new ArrayList<>();
            for (Integer innings : inningsDto) {
                inningList.add(inningService.findById(innings));
            }
            game.setInnings(inningList);
        }
        return game;
    }
}
