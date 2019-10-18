package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.GameHittingDetailsDto;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameHittingDetailsService;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameHittingDetailsRestController {
    GameHittingDetailsService gameHittingDetailsService;
    GameService gameService;
    PlayerService playerService;

    @Autowired
    public GameHittingDetailsRestController(GameHittingDetailsService gameHittingDetailsService,
                                            GameService gameService, PlayerService playerService) {
        this.gameHittingDetailsService = gameHittingDetailsService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/gameHittingDetails")
    public List<GameHittingDetailsDto> findAll() {
        List<GameHittingDetails> gameHittingDetailsList = gameHittingDetailsService.findAll();
        List<GameHittingDetailsDto> dtos = new ArrayList<>();
        for (GameHittingDetails details : gameHittingDetailsList) {
            dtos.add(convertToDto(details));
        }
        return dtos;
    }

    @GetMapping("/gameHittingDetails/{hittingDetailsId}")
    public GameHittingDetailsDto getHittingDetails(@PathVariable int hittingDetailsId) {

        GameHittingDetails theHittingDetails = gameHittingDetailsService.findById(hittingDetailsId);

        if (theHittingDetails == null) {
            throw new GameNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        return convertToDto(theHittingDetails);
    }

    @DeleteMapping("/gameHittingDetails/{hittingDetailsId}")
    public String deleteHittingDetails(@PathVariable int hittingDetailsId) {

        GameHittingDetails tempHittingDetails = gameHittingDetailsService.findById(hittingDetailsId);

        // throw exception if null

        if (tempHittingDetails == null) {
            throw new GameNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        gameHittingDetailsService.deleteById(hittingDetailsId);

        return "Deleted hitting details id - " + hittingDetailsId;
    }

    @GetMapping("/gameHittingDetails/game/{gameId}")
    public List<GameHittingDetailsDto> getGameHittingDetailsList(@PathVariable int gameId) {
        Game game = gameService.findById(gameId);
        List<GameHittingDetails> gameHittingDetailsList = gameHittingDetailsService.getGameHittingDetailsList(game);
        List<GameHittingDetailsDto> dtos = new ArrayList<>();

        for (GameHittingDetails details : gameHittingDetailsList) {
            dtos.add(convertToDto(details));
        }
        return dtos;
    }

    @GetMapping("/gameHittingDetails/game/{gameId}/player/{playerId}")
    public GameHittingDetailsDto getGameHittingDetails(@PathVariable int gameId,
                                                       @PathVariable int playerId) {
        Game game = gameService.findById(gameId);
        Player player = playerService.findById(playerId);

        GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(player, game);

        if (gameHittingDetails == null) {
            GameHittingDetailsDto gameHittingDetailsDto = new GameHittingDetailsDto();
            return gameHittingDetailsDto;
        }

        return convertToDto(gameHittingDetails);
    }

    private GameHittingDetailsDto convertToDto(GameHittingDetails gameHittingDetails) {
        GameHittingDetailsDto dto = new GameHittingDetailsDto();
        dto.setId(gameHittingDetails.getId());
        dto.setPlateAppearances(gameHittingDetails.getPlateAppearances());
        dto.setSacrificeHits(gameHittingDetails.getSacrificeHits());
        dto.setBaseForBalls(gameHittingDetails.getBaseForBalls());
        dto.setHitByPitches(gameHittingDetails.getHitByPitches());
        dto.setAtBat(gameHittingDetails.getAtBat());
        dto.setRuns(gameHittingDetails.getRuns());
        dto.setHits(gameHittingDetails.getHits());
        dto.setDoubleHit(gameHittingDetails.getDoubleHit());
        dto.setTripleHit(gameHittingDetails.getTripleHit());
        dto.setHomeRun(gameHittingDetails.getHomeRun());
        dto.setRunBattedIn(gameHittingDetails.getRunBattedIn());
        dto.setStrikeOut(gameHittingDetails.getStrikeOut());
        dto.setStolenBase(gameHittingDetails.getStolenBase());
        dto.setCaughtStealing(gameHittingDetails.getStolenBase());
        dto.setSacrificeFlies(gameHittingDetails.getSacrificeFlies());
        dto.setTotalBases(gameHittingDetails.getTotalBases());
        dto.setBattingAverage(gameHittingDetails.getBattingAverage());
        dto.setOnBasePercentage(gameHittingDetails.getOnBasePercentage());
        dto.setSluggingPercentage(gameHittingDetails.getSluggingPercentage());
        dto.setOnBaseSlugging(gameHittingDetails.getOnBaseSlugging());
        dto.setPlayer(gameHittingDetails.getPlayer().getId());
        dto.setGame(gameHittingDetails.getGame().getId());

        return dto;
    }
}
