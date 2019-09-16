package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.InningDto;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.InningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InningRestController {

    InningService inningService;

    GameService gameService;

    @Autowired
    public InningRestController(InningService inningService, GameService gameService) {
        this.inningService = inningService;
        this.gameService = gameService;
    }

    @GetMapping("/innings")
    public List<InningDto> findAll() {
        List<Inning> innings = inningService.findAll();
        List<InningDto> inningDtos = new ArrayList<>();
        for (Inning inning : innings) {
            inningDtos.add(convertToDto(inning));
        }
        return inningDtos;
    }

    @GetMapping("/innings/{inningId}")
    public InningDto getInning(@PathVariable int inningId) {

        Inning inning = inningService.findById(inningId);

        if (inning == null) {
            throw new GameNotFoundException("Inning id not found - " + inningId);
        }

        return convertToDto(inning);
    }

    @PostMapping("/innings")
    public Inning addInning(@RequestBody InningDto inningDto) {
        inningDto.setId(0);

        Inning inning = convertToEntity(inningDto);
        inningService.save(inning);

        return inning;
    }

    @PutMapping("/innings")
    public Inning updateInning(@RequestBody InningDto inningDto) {

        Inning inning = convertToEntity(inningDto);
        inningService.save(inning);

        return inning;
    }

    @DeleteMapping("/innings/{inningId}")
    public String deleteInning(@PathVariable int inningId) {

        Inning inning = inningService.findById(inningId);

        // throw exception if null
        if (inning == null) {
            throw new GameNotFoundException("Inning id not found - " + inningId);
        }

        inningService.deleteById(inningId);

        return "Deleted inning id - " + inningId;
    }

    @GetMapping("/innings/game/list/{gameId}")
    public List<InningDto> getInningsList(@PathVariable int gameId) {

        Game game = gameService.findById(gameId);
        List<Inning> innings = inningService.getInningsList(game);

        List<InningDto> inningDtos = new ArrayList<>();

        if (innings == null) {
//            InningDto inningDto = new InningDto();
//            inningDto.setGame(gameId);
//            inningDtos.add(inningDto);
//            return inningDtos;
            return null;
        }

        for (Inning inning : innings) {
            inningDtos.add(convertToDto(inning));
        }
        return inningDtos;
    }

    @GetMapping("/innings/game/{gameId}")
    public InningDto getCurrentInning(@PathVariable int gameId) {
        Game game = gameService.findById(gameId);
        Inning inning = inningService.getCurrentInning(game);

        if (inning == null) {
            return null;
        }

        return convertToDto(inning);
    }

    private InningDto convertToDto(Inning inning) {
        InningDto inningDto = new InningDto();
        inningDto.setId(inning.getId());
        inningDto.setGame(inning.getGame().getId());
        inningDto.setInningNumber(inning.getInningNumber());
        inningDto.setVisitorTeamRuns(inning.getVisitorTeamRuns());
        inningDto.setHomeTeamRuns(inning.getHomeTeamRuns());
        inningDto.setCurrentOuts(inning.getCurrentOuts());

        return inningDto;
    }

    private Inning convertToEntity(InningDto inningDto) {
        Inning inning = new Inning();
        if (inningDto.getId() != 0) {
            inning = inningService.findById(inningDto.getId());
        }
        if (inningDto.getGame() != 0) {
            inning.setGame(gameService.findById(inningDto.getGame()));
        }
        inning.setInningNumber(inningDto.getInningNumber());
        inning.setVisitorTeamRuns(inningDto.getVisitorTeamRuns());
        inning.setHomeTeamRuns(inningDto.getHomeTeamRuns());
        inning.setCurrentOuts(inningDto.getCurrentOuts());
        return inning;
    }
}
