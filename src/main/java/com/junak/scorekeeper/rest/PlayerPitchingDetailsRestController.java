package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerPitchingDetails;
import com.junak.scorekeeper.rest.error.player_pitching_details_error.PlayerPitchingDetailsNotFoundException;
import com.junak.scorekeeper.service.PlayerPitchingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerPitchingDetailsRestController {

    private PlayerPitchingDetailsService playerPitchingDetailsService;

    @Autowired
    public PlayerPitchingDetailsRestController(PlayerPitchingDetailsService playerPitchingDetailsService){
        this.playerPitchingDetailsService = playerPitchingDetailsService;
    }

    @GetMapping("/pitchingDetails")
    public List<PlayerPitchingDetails> findAll(){
        return playerPitchingDetailsService.findAll();
    }

    @GetMapping("/pitchingDetails/{pitchingDetailsId}")
    public PlayerPitchingDetails getPitchingDetails(@PathVariable int pitchingDetailsId) {

        PlayerPitchingDetails thePitchingDetails = playerPitchingDetailsService.findById(pitchingDetailsId);

        if (thePitchingDetails == null) {
            throw new PlayerPitchingDetailsNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        return thePitchingDetails;
    }
}
