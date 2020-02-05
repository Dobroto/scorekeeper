package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.GameDto;
import com.junak.scorekeeper.dto.PlayDto;
import com.junak.scorekeeper.dto.PlayerDto;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Play;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayRestController {

    private PlayService playService;

    private GameService gameService;

    @Autowired
    public PlayRestController(PlayService playService, GameService gameService) {
        this.playService = playService;
        this.gameService = gameService;
    }

    @GetMapping("/plays/{gameId}")
    public List<PlayDto> getAllPlaysInGame(@PathVariable int gameId) {
        Game theGame = gameService.findById(gameId);
        List<Play> plays = playService.getAllPlaysInGame(theGame);

        List<PlayDto> playDtos = new ArrayList<>();
        for (Play play : plays) {
            playDtos.add(convertToDto(play));
        }
        return playDtos;
    }

    private PlayDto convertToDto(Play play) {
        PlayDto playDto = new PlayDto();
        playDto.setId(play.getId());
        if (play.getGame() != null) {
            playDto.setGame(play.getGame().getId());
        }
        if (play.getInning() != null) {
            playDto.setInning(play.getInning().getId());
        }
        playDto.setAction(play.getAction());

        return playDto;
    }
}
