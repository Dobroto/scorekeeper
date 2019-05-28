package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerFieldingDetails;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
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

    @GetMapping("/playerFieldingDetails")
    public List<PlayerFieldingDetails> findAll(){
        return playerFieldingDetailsService.findAll();
    }

    @GetMapping("/playerFieldingDetails/{fieldingDetailsId}")
    public PlayerFieldingDetails getFieldingDetails(@PathVariable int fieldingDetailsId) {

        PlayerFieldingDetails theFieldingDetails = playerFieldingDetailsService.findById(fieldingDetailsId);

        if (theFieldingDetails == null) {
            throw new GameNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        return theFieldingDetails;
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
}
