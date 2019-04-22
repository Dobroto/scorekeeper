package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.rest.error.game_fielding_details_error.GameFieldingDetailsNotFoundException;
import com.junak.scorekeeper.service.GameFieldingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameFieldingDetailsRestController {
    GameFieldingDetailsService gameFieldingDetailsService;

    @Autowired
    public GameFieldingDetailsRestController(GameFieldingDetailsService gameFieldingDetailsService) {
        this.gameFieldingDetailsService = gameFieldingDetailsService;
    }

    @GetMapping("/gameFieldingDetails")
    public List<GameFieldingDetails> findAll(){
        return gameFieldingDetailsService.findAll();
    }

    @GetMapping("/gameFieldingDetails/{fieldingDetailsId}")
    public GameFieldingDetails getFieldingDetails(@PathVariable int fieldingDetailsId) {

        GameFieldingDetails theFieldingDetails = gameFieldingDetailsService.findById(fieldingDetailsId);

        if (theFieldingDetails == null) {
            throw new GameFieldingDetailsNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        return theFieldingDetails;
    }

    @DeleteMapping("/gameFieldingDetails/{fieldingDetailsId}")
    public String deleteFieldingDetails(@PathVariable int fieldingDetailsId) {

        GameFieldingDetails tempFieldingDetails = gameFieldingDetailsService.findById(fieldingDetailsId);

        // throw exception if null

        if (tempFieldingDetails == null) {
            throw new GameFieldingDetailsNotFoundException("Fielding details id not found - " + fieldingDetailsId);
        }

        tempFieldingDetails.getGame().setGameFieldingDetails(null);

        gameFieldingDetailsService.deleteById(fieldingDetailsId);

        return "Deleted fielding details id - " + fieldingDetailsId;
    }
}
