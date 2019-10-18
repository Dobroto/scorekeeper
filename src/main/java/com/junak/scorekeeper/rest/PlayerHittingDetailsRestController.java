package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.PlayerHittingDetailsDto;
import com.junak.scorekeeper.entity.PlayerHittingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.PlayerHittingDetailsService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerHittingDetailsRestController {

    PlayerHittingDetailsService playerHittingDetailsService;

    @Autowired
    public PlayerHittingDetailsRestController(PlayerHittingDetailsService playerHittingDetailsService) {
        this.playerHittingDetailsService = playerHittingDetailsService;
    }

    @GetMapping("/playerHittingDetails")
    public List<PlayerHittingDetailsDto> findAll(){
        List<PlayerHittingDetails> playerHittingDetails = playerHittingDetailsService.findAll();
        List<PlayerHittingDetailsDto> dtos = new ArrayList<>();
        for (PlayerHittingDetails hittingDetails : playerHittingDetails) {
            dtos.add(convertToDto(hittingDetails));
        }
        return dtos;
    }

    @GetMapping("/playerHittingDetails/{hittingDetailsId}")
    public PlayerHittingDetailsDto getHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails theHittingDetails = playerHittingDetailsService.findById(hittingDetailsId);

        if (theHittingDetails == null) {
            throw new GameNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        return convertToDto(theHittingDetails);
    }

    @DeleteMapping("/playerHittingDetails/{hittingDetailsId}")
    public String deleteHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails tempHittingDetails = playerHittingDetailsService.findById(hittingDetailsId);

        // throw exception if null

        if (tempHittingDetails == null) {
            throw new GameNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        tempHittingDetails.getPlayer().setPlayerHittingDetails(null);

        playerHittingDetailsService.deleteById(hittingDetailsId);

        return "Deleted hitting details id - " + hittingDetailsId;
    }

    private PlayerHittingDetailsDto convertToDto(PlayerHittingDetails playerHittingDetails) {
        PlayerHittingDetailsDto dto = new PlayerHittingDetailsDto();
        dto.setId(playerHittingDetails.getId());
        dto.setGames(playerHittingDetails.getGames());
        dto.setPlateAppearances(playerHittingDetails.getPlateAppearances());
        dto.setSacrificeHits(playerHittingDetails.getSacrificeHits());
        dto.setBaseForBalls(playerHittingDetails.getBaseForBalls());
        dto.setHitByPitches(playerHittingDetails.getHitByPitches());
        dto.setAtBat(playerHittingDetails.getAtBat());
        dto.setRuns(playerHittingDetails.getRuns());
        dto.setHits(playerHittingDetails.getHits());
        dto.setDoubleHit(playerHittingDetails.getDoubleHit());
        dto.setTripleHit(playerHittingDetails.getTripleHit());
        dto.setHomeRun(playerHittingDetails.getHomeRun());
        dto.setRunBattedIn(playerHittingDetails.getRunBattedIn());
        dto.setStrikeOut(playerHittingDetails.getStrikeOut());
        dto.setStolenBase(playerHittingDetails.getStolenBase());
        dto.setCaughtStealing(playerHittingDetails.getStolenBase());
        dto.setSacrificeFlies(playerHittingDetails.getSacrificeFlies());
        dto.setTotalBases(playerHittingDetails.getTotalBases());
        dto.setBattingAverage(playerHittingDetails.getBattingAverage());
        dto.setOnBasePercentage(playerHittingDetails.getOnBasePercentage());
        dto.setSluggingPercentage(playerHittingDetails.getSluggingPercentage());
        dto.setOnBaseSlugging(playerHittingDetails.getOnBaseSlugging());
        dto.setPlayer(playerHittingDetails.getPlayer().getId());

        return dto;
    }
}