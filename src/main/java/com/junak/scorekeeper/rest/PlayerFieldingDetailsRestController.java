package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerFieldingDetails;
import com.junak.scorekeeper.rest.error.player_fielding_details_error.PlayerFieldingDetailsNotFoundException;
import com.junak.scorekeeper.service.PlayerFieldingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerFieldingDetailsRestController {
    PlayerFieldingDetailsService playerFieldingDetailsService;

    @Autowired
    public PlayerFieldingDetailsRestController(PlayerFieldingDetailsService playerFieldingDetailsService) {
        this.playerFieldingDetailsService = playerFieldingDetailsService;
    }

    @GetMapping("/fieldingDetails")
    public List<PlayerFieldingDetails> findAll(){
        return playerFieldingDetailsService.findAll();
    }

    @GetMapping("/fieldingDetails/{fieldingDetailsId}")
    public PlayerFieldingDetails getFieldingDetails(@PathVariable int fieldingDetailsId) {

        PlayerFieldingDetails theFieldingDetails = playerFieldingDetailsService.findById(fieldingDetailsId);

        if (theFieldingDetails == null) {
            throw new PlayerFieldingDetailsNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        return theFieldingDetails;
    }

    @DeleteMapping("/fieldingDetails/{fieldingDetailsId}")
    public String deleteFieldingDetails(@PathVariable int fieldingDetailsId) {

        PlayerFieldingDetails tempFieldingDetails = playerFieldingDetailsService.findById(fieldingDetailsId);

        // throw exception if null

        if (tempFieldingDetails == null) {
            throw new PlayerFieldingDetailsNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        tempFieldingDetails.getPlayer().setPlayerFieldingDetails(null);

        playerFieldingDetailsService.deleteById(fieldingDetailsId);

        return "Deleted fielding details id - " + fieldingDetailsId;
    }
}