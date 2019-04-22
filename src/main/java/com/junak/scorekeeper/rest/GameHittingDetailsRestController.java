package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.GameHittingDetails;
import com.junak.scorekeeper.rest.error.game_hitting_details_error.GameHittingDetailsNotFoundException;
import com.junak.scorekeeper.service.GameHittingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameHittingDetailsRestController {
    GameHittingDetailsService gameHittingDetailsService;

    @Autowired
    public GameHittingDetailsRestController(GameHittingDetailsService gameHittingDetailsService) {
        this.gameHittingDetailsService = gameHittingDetailsService;
    }

    @GetMapping("/gameHittingDetails")
    public List<GameHittingDetails> findAll(){
        return gameHittingDetailsService.findAll();
    }

    @GetMapping("/gameHittingDetails/{hittingDetailsId}")
    public GameHittingDetails getHittingDetails(@PathVariable int hittingDetailsId) {

        GameHittingDetails theHittingDetails = gameHittingDetailsService.findById(hittingDetailsId);

        if (theHittingDetails == null) {
            throw new GameHittingDetailsNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        return theHittingDetails;
    }

    @DeleteMapping("/gameHittingDetails/{hittingDetailsId}")
    public String deleteHittingDetails(@PathVariable int hittingDetailsId) {

        GameHittingDetails tempHittingDetails = gameHittingDetailsService.findById(hittingDetailsId);

        // throw exception if null

        if (tempHittingDetails == null) {
            throw new GameHittingDetailsNotFoundException("Hitting details id not found - " + hittingDetailsId);
        }

        tempHittingDetails.getGame().setGameHittingDetails(null);

        gameHittingDetailsService.deleteById(hittingDetailsId);

        return "Deleted hitting details id - " + hittingDetailsId;
    }
}
