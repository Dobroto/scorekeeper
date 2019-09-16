package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.FinalResultDto;
import com.junak.scorekeeper.entity.FinalResult;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.FinalResultService;
import com.junak.scorekeeper.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FinalResultRestController {

    private FinalResultService finalResultService;
    private GameService gameService;

    @Autowired
    public FinalResultRestController(FinalResultService finalResultService, GameService gameService) {
        this.finalResultService = finalResultService;
        this.gameService = gameService;
    }

    @GetMapping("/finalResults")
    public List<FinalResultDto> findAll() {
        List<FinalResult> finalResults = finalResultService.findAll();
        List<FinalResultDto> finalResultDtos = new ArrayList<>();
        for (FinalResult finalResult : finalResults) {
            finalResultDtos.add(convertToDto(finalResult));
        }
        return finalResultDtos;
    }

    @GetMapping("/finalResults/{finalResultId}")
    public FinalResultDto getFinalResult(@PathVariable int finalResultId) {

        FinalResult finalResult = finalResultService.findById(finalResultId);

        if (finalResult == null) {
            throw new GameNotFoundException("Final result id not found - " + finalResultId);
        }

        return convertToDto(finalResult);
    }

    @PostMapping("/finalResults")
    public FinalResult addFinalResult(@RequestBody FinalResultDto finalResultDto) {
        finalResultDto.setId(0);

        FinalResult finalResult = convertToEntity(finalResultDto);
        finalResultService.save(finalResult);

        return finalResult;
    }

    @PutMapping("/finalResults")
    public FinalResult updateFinalResult(@RequestBody FinalResultDto finalResultDto) {

        FinalResult finalResult = convertToEntity(finalResultDto);
        finalResultService.save(finalResult);

        return finalResult;
    }

    @DeleteMapping("/finalResults/{finalResultId}")
    public String deleteFinalResult(@PathVariable int finalResultId) {

        FinalResult finalResult = finalResultService.findById(finalResultId);

        // throw exception if null
        if (finalResult == null) {
            throw new GameNotFoundException("FinalResult id not found - " + finalResultId);
        }

        finalResultService.deleteById(finalResultId);

        return "Deleted finalResult id - " + finalResultId;
    }

    private FinalResultDto convertToDto(FinalResult finalResult) {
        FinalResultDto dto = new FinalResultDto();
        dto.setId(finalResult.getId());
        dto.setVisitorTeamScore(finalResult.getVisitorTeamScore());
        dto.setHomeTeamScore(finalResult.getHomeTeamScore());
        dto.setVisitorTeamHits(finalResult.getVisitorTeamHits());
        dto.setHomeTeamHits(finalResult.getHomeTeamHits());
        dto.setVisitorTeamErrors(finalResult.getVisitorTeamErrors());
        dto.setHomeTeamErrors(finalResult.getHomeTeamErrors());
        dto.setGame(finalResult.getGame().getId());

        return dto;
    }

    private FinalResult convertToEntity(FinalResultDto dto) {
        FinalResult finalResult = new FinalResult();

        if (dto.getId() != 0) {
            finalResult = finalResultService.findById(dto.getId());
        }

        finalResult.setVisitorTeamScore(dto.getVisitorTeamScore());
        finalResult.setHomeTeamScore(dto.getHomeTeamScore());
        finalResult.setVisitorTeamHits(dto.getVisitorTeamHits());
        finalResult.setHomeTeamHits(dto.getHomeTeamHits());
        finalResult.setVisitorTeamErrors(dto.getVisitorTeamErrors());
        finalResult.setHomeTeamErrors(dto.getHomeTeamErrors());
        if (dto.getGame() != 0) {
            finalResult.setGame(gameService.findById(dto.getGame()));
        }
        return finalResult;
    }
}
