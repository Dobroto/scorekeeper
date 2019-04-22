package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.rest.error.game_pitching_details_error.GamePitchingDetailsNotFoundException;
import com.junak.scorekeeper.service.GamePitchingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GamePitchingDetailsRestController {
    private GamePitchingDetailsService gamePitchingDetailsService;

    @Autowired
    public GamePitchingDetailsRestController(GamePitchingDetailsService gamePitchingDetailsService) {
        this.gamePitchingDetailsService = gamePitchingDetailsService;
    }

    @GetMapping("/gamePitchingDetails")
    public List<GamePitchingDetails> findAll() {
        return gamePitchingDetailsService.findAll();
    }

    @GetMapping("/gamePitchingDetails/{pitchingDetailsId}")
    public GamePitchingDetails getPitchingDetails(@PathVariable int pitchingDetailsId) {

        GamePitchingDetails thePitchingDetails = gamePitchingDetailsService.findById(pitchingDetailsId);

        if (thePitchingDetails == null) {
            throw new GamePitchingDetailsNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        return thePitchingDetails;
    }

    @DeleteMapping("/gamePitchingDetails/{pitchingDetailsId}")
    public String deletePitchingDetails(@PathVariable int pitchingDetailsId) {

        GamePitchingDetails tempPitchingDetails = gamePitchingDetailsService.findById(pitchingDetailsId);

        // throw exception if null

        if (tempPitchingDetails == null) {
            throw new GamePitchingDetailsNotFoundException("Pitching details id not found - " + pitchingDetailsId);
        }

        tempPitchingDetails.getGame().setGamePitchingDetails(null);

        gamePitchingDetailsService.deleteById(pitchingDetailsId);

        return "Deleted pitching details id - " + pitchingDetailsId;
    }
}
