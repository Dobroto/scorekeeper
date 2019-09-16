package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.GamePitchingDetailsDto;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GamePitchingDetailsService;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GamePitchingDetailsRestController {
    private GamePitchingDetailsService gamePitchingDetailsService;
    GameService gameService;
    PlayerService playerService;

    @Autowired
    public GamePitchingDetailsRestController(GamePitchingDetailsService gamePitchingDetailsService,
                                             GameService gameService, PlayerService playerService) {
        this.gamePitchingDetailsService = gamePitchingDetailsService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/gamePitchingDetails")
    public List<GamePitchingDetailsDto> findAll() {
        List<GamePitchingDetails> gamePitchingDetailsList = gamePitchingDetailsService.findAll();
        List<GamePitchingDetailsDto> dtos = new ArrayList<>();
        for (GamePitchingDetails details : gamePitchingDetailsList) {
            dtos.add(convertToDto(details));
        }
        return dtos;
    }

    @GetMapping("/gamePitchingDetails/{pitchingDetailsId}")
    public GamePitchingDetailsDto getPitchingDetails(@PathVariable int pitchingDetailsId) {

        GamePitchingDetails thePitchingDetails = gamePitchingDetailsService.findById(pitchingDetailsId);

        if (thePitchingDetails == null) {
            throw new GameNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        return convertToDto(thePitchingDetails);
    }

    @DeleteMapping("/gamePitchingDetails/{pitchingDetailsId}")
    public String deletePitchingDetails(@PathVariable int pitchingDetailsId) {

        GamePitchingDetails tempPitchingDetails = gamePitchingDetailsService.findById(pitchingDetailsId);

        // throw exception if null

        if (tempPitchingDetails == null) {
            throw new GameNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        gamePitchingDetailsService.deleteById(pitchingDetailsId);

        return "Deleted pitching details id - " + pitchingDetailsId;
    }

    @GetMapping("/gamePitchingDetails/game/{gameId}")
    public List<GamePitchingDetailsDto> getGamePitchingDetailsList(@PathVariable int gameId) {
        Game game = gameService.findById(gameId);
        List<GamePitchingDetails> gamePitchingDetailsList = gamePitchingDetailsService.getGamePitchingDetailsList(game);
        List<GamePitchingDetailsDto> dtos = new ArrayList<>();

        for (GamePitchingDetails details : gamePitchingDetailsList) {
            dtos.add(convertToDto(details));
        }
        return dtos;
    }

    @GetMapping("/gamePitchingDetails/game/{gameId}/player/{playerId}")
    public GamePitchingDetailsDto getGamePitchingDetails(@PathVariable int gameId,
                                                       @PathVariable int playerId) {
        Game game = gameService.findById(gameId);
        Player player = playerService.findById(playerId);

        GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(player, game);

        if (gamePitchingDetails == null) {
            GamePitchingDetailsDto gamePitchingDetailsDto = new GamePitchingDetailsDto();
            return gamePitchingDetailsDto;
        }

        return convertToDto(gamePitchingDetails);
    }

    private GamePitchingDetailsDto convertToDto(GamePitchingDetails gamePitchingDetails) {
        GamePitchingDetailsDto dto = new GamePitchingDetailsDto();
        dto.setId(gamePitchingDetails.getId());
        dto.setWins(gamePitchingDetails.getWins());
        dto.setLoses(gamePitchingDetails.getLoses());
        dto.setEarnedRuns(gamePitchingDetails.getEarnedRuns());
        dto.setInningsPitched(gamePitchingDetails.getInningsPitched());
        dto.setEarnedRunAverage(gamePitchingDetails.getEarnedRunAverage());
        dto.setSaves(gamePitchingDetails.getSaves());
        dto.setSaveOpportunities(gamePitchingDetails.getSaveOpportunities());
        dto.setHits(gamePitchingDetails.getHits());
        dto.setRuns(gamePitchingDetails.getRuns());
        dto.setHomeRuns(gamePitchingDetails.getHomeRuns());
        dto.setBasesOnBalls(gamePitchingDetails.getBasesOnBalls());
        dto.setStrikeOuts(gamePitchingDetails.getStrikeOuts());
        dto.setAverage(gamePitchingDetails.getAverage());
        dto.setWhips(gamePitchingDetails.getWhips());
        dto.setPlayer(gamePitchingDetails.getPlayer().getId());
        dto.setGame(gamePitchingDetails.getGame().getId());

        return dto;
    }
}
