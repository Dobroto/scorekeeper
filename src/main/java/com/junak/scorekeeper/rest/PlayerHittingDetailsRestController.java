package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerHittingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
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

    @GetMapping("/playerHittingDetails")
    public List<PlayerHittingDetails> findAll(){
        return playerHittingDetailsService.findAll();
    }

    @GetMapping("/playerHittingDetails/{hittingDetailsId}")
    public PlayerHittingDetails getHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails theHittingDetails = playerHittingDetailsService.findById(hittingDetailsId);

        if (theHittingDetails == null) {
            throw new GameNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        return theHittingDetails;
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
}