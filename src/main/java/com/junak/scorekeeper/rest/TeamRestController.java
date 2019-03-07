package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.rest.errors.TeamNotFoundException;
import com.junak.scorekeeper.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamRestController {
    private TeamService teamService;

    @Autowired
    public TeamRestController (TeamService teamService){
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public List<Team> findAll() {
        return teamService.findAll();
    }

    @GetMapping("/teams/{teamId}")
    public Team getTeam(@PathVariable int teamId) {

        Team theTeam = teamService.findById(teamId);

        if (theTeam == null) {
            throw new TeamNotFoundException("Team id not found - " + teamId);
        }

        return theTeam;
    }

    @PostMapping("/teams")
    public Team addTeam(@RequestBody Team theTeam) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        theTeam.setId(0);

        teamService.save(theTeam);

        return theTeam;
    }

    @PutMapping("/teams")
    public Team updateTeam(@RequestBody Team theTeam) {

        teamService.save(theTeam);

        return theTeam;
    }

    @DeleteMapping("/teams/{teamId}")
    public String deleteTeam(@PathVariable int teamId) {

        Team tempTeam = teamService.findById(teamId);

        // throw exception if null

        if (tempTeam == null) {
            throw new TeamNotFoundException("Team id not found - " + teamId);
        }

        teamService.deleteById(teamId);

        return "Deleted team id - " + teamId;
    }
}
