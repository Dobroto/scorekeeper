package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.PlayerHittingDetails;
import com.junak.scorekeeper.service.PlayerHittingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/hittingDetails/{playerId}")
    public PlayerHittingDetails getPlayerHittingDetails(@PathVariable int playerId){
        return playerHittingDetailsService.getPlayerHittingDetails(playerId);
    }
}
