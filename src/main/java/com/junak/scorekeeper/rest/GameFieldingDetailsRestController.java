package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.GameFieldingDetailsDto;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameFieldingDetailsService;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameFieldingDetailsRestController {
    private static final Logger logger = LoggerFactory.getLogger(GameFieldingDetailsRestController.class);

    private GameFieldingDetailsService gameFieldingDetailsService;
    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public GameFieldingDetailsRestController(GameFieldingDetailsService gameFieldingDetailsService,
                                             GameService gameService, PlayerService playerService) {
        this.gameFieldingDetailsService = gameFieldingDetailsService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/gameFieldingDetails")
    public List<GameFieldingDetailsDto> findAll() {
        List<GameFieldingDetails> gameFieldingDetailsList = gameFieldingDetailsService.findAll();
        List<GameFieldingDetailsDto> dtos = new ArrayList<>();
        for (GameFieldingDetails details : gameFieldingDetailsList) {
            dtos.add(convertToDto(details));
        }
        return dtos;
    }

    @GetMapping("/gameFieldingDetails/{fieldingDetailsId}")
    public GameFieldingDetailsDto getFieldingDetails(@PathVariable int fieldingDetailsId) {

        GameFieldingDetails theFieldingDetails = gameFieldingDetailsService.findById(fieldingDetailsId);

        if (theFieldingDetails == null) {
            throw new GameNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        return convertToDto(theFieldingDetails);
    }

    @DeleteMapping("/gameFieldingDetails/{fieldingDetailsId}")
    public String deleteFieldingDetails(@PathVariable int fieldingDetailsId) {

        GameFieldingDetails tempFieldingDetails = gameFieldingDetailsService.findById(fieldingDetailsId);

        // throw exception if null

        if (tempFieldingDetails == null) {
            throw new GameNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        gameFieldingDetailsService.deleteById(fieldingDetailsId);

        return "Deleted fielding details id - " + fieldingDetailsId;
    }

    @GetMapping("/gameFieldingDetails/game/{gameId}")
    public List<GameFieldingDetailsDto> getGameFieldingDetailsList(@PathVariable int gameId) {
        Game game = gameService.findById(gameId);
        List<GameFieldingDetails> gameFieldingDetailsList = gameFieldingDetailsService.getGameFieldingDetailsList(game);
        List<GameFieldingDetailsDto> dtos = new ArrayList<>();

        for (GameFieldingDetails details : gameFieldingDetailsList) {
            dtos.add(convertToDto(details));
        }
        return dtos;
    }

    @GetMapping("/gameFieldingDetails/game/{gameId}/player/{playerId}")
    public GameFieldingDetailsDto getGameFieldingDetails(@PathVariable int gameId,
                                                         @PathVariable int playerId) {
        Game game = gameService.findById(gameId);
        Player player = playerService.findById(playerId);

        GameFieldingDetails gameFieldingDetails = gameFieldingDetailsService.getGameFieldingDetails(player, game);

        if (gameFieldingDetails == null) {
            logger.info("There is no game fielding details of player with id {} and game with id {}", playerId, gameId);
        }

        return convertToDto(gameFieldingDetails);
    }

    private GameFieldingDetailsDto convertToDto(GameFieldingDetails gameFieldingDetails) {
        GameFieldingDetailsDto dto = new GameFieldingDetailsDto();
        if (gameFieldingDetails != null) {
            dto.setId(gameFieldingDetails.getId());
            dto.setInnings(gameFieldingDetails.getInnings());
            dto.setTotalChances(gameFieldingDetails.getTotalChances());
            dto.setPutOut(gameFieldingDetails.getPutOut());
            dto.setAssists(gameFieldingDetails.getAssists());
            dto.setErrors(gameFieldingDetails.getErrors());
            dto.setDoublePlays(gameFieldingDetails.getDoublePlays());
            dto.setAverageOfErrorsPerTotalChances(gameFieldingDetails.getAverageOfErrorsPerTotalChances());
            dto.setPlayer(gameFieldingDetails.getPlayer().getId());
            dto.setGame(gameFieldingDetails.getGame().getId());
        }
        return dto;
    }
}
