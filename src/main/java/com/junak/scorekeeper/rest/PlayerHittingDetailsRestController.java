package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerHittingDetails;
import com.junak.scorekeeper.rest.error.player_hitting_details_error.PlayerHittingDetailsNotFoundException;
import com.junak.scorekeeper.service.PlayerHittingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerHittingDetailsRestController {

    PlayerHittingDetailsService playerHittingDetailsService;

    @Autowired
    public PlayerHittingDetailsRestController(PlayerHittingDetailsService playerHittingDetailsService) {
        this.playerHittingDetailsService = playerHittingDetailsService;
    }

    @GetMapping("/hittingDetails")
    public List<PlayerHittingDetails> findAll(){
        return playerHittingDetailsService.findAll();
    }

    @GetMapping("/hittingDetails/{hittingDetailsId}")
    public PlayerHittingDetails getHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails theHittingDetails = playerHittingDetailsService.findById(hittingDetailsId);

        if (theHittingDetails == null) {
            throw new PlayerHittingDetailsNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        return theHittingDetails;
    }

    @DeleteMapping("/hittingDetails/{hittingDetailsId}")
    public String deleteHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails tempHittingDetails = playerHittingDetailsService.findById(hittingDetailsId);

        // throw exception if null

        if (tempHittingDetails == null) {
            throw new PlayerHittingDetailsNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        tempHittingDetails.getPlayer().setPlayerHittingDetails(null);

        playerHittingDetailsService.deleteById(hittingDetailsId);

        return "Deleted hitting details id - " + hittingDetailsId;
    }
}