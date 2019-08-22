package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.PlayerFieldingDetailsDto;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.PlayerFieldingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.PlayerFieldingDetailsService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerFieldingDetailsRestController {
    PlayerFieldingDetailsService playerFieldingDetailsService;

    @Autowired
    public PlayerFieldingDetailsRestController(PlayerFieldingDetailsService playerFieldingDetailsService) {
        this.playerFieldingDetailsService = playerFieldingDetailsService;
    }

    @GetMapping("/playerFieldingDetails")
    public List<PlayerFieldingDetailsDto> findAll() {
        List<PlayerFieldingDetails> playerFieldingDetails = playerFieldingDetailsService.findAll();
        List<PlayerFieldingDetailsDto> dtos = new ArrayList<>();
        for (PlayerFieldingDetails fieldingDetails : playerFieldingDetails) {
            dtos.add(convertToDto(fieldingDetails));
        }
        return dtos;
    }

    @GetMapping("/playerFieldingDetails/{fieldingDetailsId}")
    public PlayerFieldingDetailsDto getFieldingDetails(@PathVariable int fieldingDetailsId) {

        PlayerFieldingDetails theFieldingDetails = playerFieldingDetailsService.findById(fieldingDetailsId);

        if (theFieldingDetails == null) {
            throw new GameNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        return convertToDto(theFieldingDetails);
    }

    @DeleteMapping("/playerFieldingDetails/{fieldingDetailsId}")
    public String deleteFieldingDetails(@PathVariable int fieldingDetailsId) {

        PlayerFieldingDetails tempFieldingDetails = playerFieldingDetailsService.findById(fieldingDetailsId);

        // throw exception if null

        if (tempFieldingDetails == null) {
            throw new GameNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        tempFieldingDetails.getPlayer().setPlayerFieldingDetails(null);

        playerFieldingDetailsService.deleteById(fieldingDetailsId);

        return "Deleted fielding details id - " + fieldingDetailsId;
    }

    private PlayerFieldingDetailsDto convertToDto(PlayerFieldingDetails playerFieldingDetails) {
        PlayerFieldingDetailsDto dto = new PlayerFieldingDetailsDto();
        dto.setId(playerFieldingDetails.getId());
        dto.setGamesStarted(playerFieldingDetails.getGamesStarted());
        dto.setGamesPlayed(playerFieldingDetails.getGamesPlayed());
        dto.setInnings(playerFieldingDetails.getInnings());
        dto.setTotalChances(playerFieldingDetails.getTotalChances());
        dto.setPutOut(playerFieldingDetails.getPutOut());
        dto.setAssists(playerFieldingDetails.getAssists());
        dto.setErrors(playerFieldingDetails.getErrors());
        dto.setDoublePlays(playerFieldingDetails.getDoublePlays());
        dto.setAverageOfErrorsPerTotalChances(playerFieldingDetails.getAverageOfErrorsPerTotalChances());
        dto.setPlayer(playerFieldingDetails.getPlayer().getId());

        return dto;
    }
}
