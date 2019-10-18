package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.PlayerPitchingDetailsDto;
import com.junak.scorekeeper.entity.PlayerPitchingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.PlayerPitchingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerPitchingDetailsRestController {

    private PlayerPitchingDetailsService playerPitchingDetailsService;

    @Autowired
    public PlayerPitchingDetailsRestController(PlayerPitchingDetailsService playerPitchingDetailsService) {
        this.playerPitchingDetailsService = playerPitchingDetailsService;
    }

    @GetMapping("/playerPitchingDetails")
    public List<PlayerPitchingDetailsDto> findAll() {
        List<PlayerPitchingDetails> playerPitchingDetails = playerPitchingDetailsService.findAll();
        List<PlayerPitchingDetailsDto> dtos = new ArrayList<>();
        for (PlayerPitchingDetails pitchingDetails : playerPitchingDetails) {
            dtos.add(convertToDto(pitchingDetails));
        }
        return dtos;
    }

    @GetMapping("/playerPitchingDetails/{pitchingDetailsId}")
    public PlayerPitchingDetailsDto getPitchingDetails(@PathVariable int pitchingDetailsId) {

        PlayerPitchingDetails thePitchingDetails = playerPitchingDetailsService.findById(pitchingDetailsId);

        if (thePitchingDetails == null) {
            throw new GameNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        return convertToDto(thePitchingDetails);
    }

    @DeleteMapping("/playerPitchingDetails/{pitchingDetailsId}")
    public String deletePitchingDetails(@PathVariable int pitchingDetailsId) {

        PlayerPitchingDetails tempPitchingDetails = playerPitchingDetailsService.findById(pitchingDetailsId);

        // throw exception if null

        if (tempPitchingDetails == null) {
            throw new GameNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        tempPitchingDetails.getPlayer().setPlayerPitchingDetails(null);

        playerPitchingDetailsService.deleteById(pitchingDetailsId);

        return "Deleted pitching details id - " + pitchingDetailsId;
    }

    private PlayerPitchingDetailsDto convertToDto(PlayerPitchingDetails playerPitchingDetails) {
        PlayerPitchingDetailsDto dto = new PlayerPitchingDetailsDto();
        dto.setId(playerPitchingDetails.getId());
        dto.setWins(playerPitchingDetails.getWins());
        dto.setLoses(playerPitchingDetails.getLoses());
        dto.setEarnedRuns(playerPitchingDetails.getEarnedRuns());
        dto.setInningsPitched(playerPitchingDetails.getInningsPitched());
        dto.setEarnedRunAverage(playerPitchingDetails.getEarnedRunAverage());
        dto.setGames(playerPitchingDetails.getGames());
        dto.setGamesStarted(playerPitchingDetails.getGamesStarted());
        dto.setSaves(playerPitchingDetails.getSaves());
        dto.setSaveOpportunities(playerPitchingDetails.getSaveOpportunities());
        dto.setHits(playerPitchingDetails.getHits());
        dto.setRuns(playerPitchingDetails.getRuns());
        dto.setHomeRuns(playerPitchingDetails.getHomeRuns());
        dto.setBasesOnBalls(playerPitchingDetails.getBasesOnBalls());
        dto.setStrikeOuts(playerPitchingDetails.getStrikeOuts());
        dto.setAverage(playerPitchingDetails.getAverage());
        dto.setWhips(playerPitchingDetails.getWhips());
        dto.setPlayer(playerPitchingDetails.getPlayer().getId());

        return dto;
    }
}
