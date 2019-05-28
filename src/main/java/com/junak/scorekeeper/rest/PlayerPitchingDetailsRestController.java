package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerPitchingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.PlayerPitchingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<PlayerPitchingDetails> findAll() {
        return playerPitchingDetailsService.findAll();
    }

    @GetMapping("/playerPitchingDetails/{pitchingDetailsId}")
    public PlayerPitchingDetails getPitchingDetails(@PathVariable int pitchingDetailsId) {

        PlayerPitchingDetails thePitchingDetails = playerPitchingDetailsService.findById(pitchingDetailsId);

        if (thePitchingDetails == null) {
            throw new GameNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        return thePitchingDetails;
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
}
