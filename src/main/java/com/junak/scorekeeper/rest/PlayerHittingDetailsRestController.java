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

//    @GetMapping("/hittingDetails/{playerId}")
//    public PlayerHittingDetails getPlayerHittingDetails(@PathVariable int playerId){
//        return playerHittingDetailsService.getPlayerHittingDetails(playerId);
//    }

    @GetMapping("/hittingDetails/{hittingDetailsId}")
    public PlayerHittingDetails getHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails theDetails = playerHittingDetailsService.findById(hittingDetailsId);

        if (theDetails == null) {
            throw new PlayerHittingDetailsNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        return theDetails;
    }

    @DeleteMapping("/hittingDetails/{hittingDetailsId}")
    public String deleteHittingDetails(@PathVariable int hittingDetailsId) {

        PlayerHittingDetails tempDetails = playerHittingDetailsService.findById(hittingDetailsId);

        // throw exception if null

        if (tempDetails == null) {
            throw new PlayerHittingDetailsNotFoundException("Details id not found - " + hittingDetailsId);
        }

        tempDetails.getPlayer().setHittingDetails(null);

        playerHittingDetailsService.deleteById(hittingDetailsId);

        return "Deleted details id - " + hittingDetailsId;
    }
}
