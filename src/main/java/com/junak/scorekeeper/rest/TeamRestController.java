package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.TeamDto;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import com.junak.scorekeeper.service.interfaces.TeamService;
import org.aspectj.apache.bcel.util.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamRestController {
    private TeamService teamService;
    private PlayerService playerService;
    private GameService gameService;

    @Autowired
    public TeamRestController(TeamService teamService, PlayerService playerService, GameService gameService) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @GetMapping("/teams")
    public List<TeamDto> findAll() {
        List<Team> teams = teamService.findAll();
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            teamDtos.add(convertToDto(team));
        }
        return teamDtos;
    }

    @GetMapping("/teams/{teamId}")
    public TeamDto getTeam(@PathVariable int teamId) {

        Team theTeam = teamService.findById(teamId);

        if (theTeam == null) {
            throw new GameNotFoundException("Team id not found - " + teamId);
        }

        return convertToDto(theTeam);
    }

    @PostMapping("/teams")
    public Team addTeam(@RequestBody TeamDto theTeamDto) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        theTeamDto.setId(0);

        Team theTeam = convertToEntity(theTeamDto);

        teamService.save(theTeam);

        return theTeam;
    }

    @PutMapping("/teams")
    public Team updateTeam(@RequestBody TeamDto theTeamDto) {

        Team theTeam = convertToEntity(theTeamDto);
        teamService.save(theTeam);

        return theTeam;
    }

    @DeleteMapping("/teams/{teamId}")
    public String deleteTeam(@PathVariable int teamId) {

        Team tempTeam = teamService.findById(teamId);

        // throw exception if null

        if (tempTeam == null) {
            throw new GameNotFoundException("Team id not found - " + teamId);
        }

        teamService.deleteById(teamId);

        return "Deleted team id - " + teamId;
    }

    private TeamDto convertToDto(Team team) {
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setTeamNameLong(team.getTeamNameLong());
        dto.setTeamNameShort(team.getTeamNameShort());

        if ((team.getPlayers() != null) && (team.getPlayers().size() != 0)) {
            List<Integer> playerDtos = new ArrayList<>();
            List<Player> players = team.getPlayers();
            for (Player player : players) {
                playerDtos.add(player.getId());
            }
            dto.setPlayers(playerDtos);
        }

        if ((team.getHomeGames() != null) && (team.getHomeGames().size() != 0)) {
            List<Integer> homeGameDtos = new ArrayList<>();
            List<Game> homeGames = team.getHomeGames();
            for (Game homeGame : homeGames) {
                homeGameDtos.add(homeGame.getId());
            }
            dto.setHomeGames(homeGameDtos);
        }

        if ((team.getVisitorGames() != null) && (team.getVisitorGames().size() != 0)) {
            List<Integer> visitorGameDtos = new ArrayList<>();
            List<Game> visitorGames = team.getVisitorGames();
            for (Game visitorGame : visitorGames) {
                visitorGameDtos.add(visitorGame.getId());
            }
            dto.setVisitorGames(visitorGameDtos);
        }

        return dto;
    }

    private Team convertToEntity(TeamDto dto) {
        Team team = new Team();

        if (dto.getId() != 0) {
            team = teamService.findById(dto.getId());
        }

        team.setTeamNameLong(dto.getTeamNameLong());
        team.setTeamNameShort(dto.getTeamNameShort());
        if ((dto.getPlayers() != null) && (dto.getPlayers().size() != 0)) {
            List<Integer> playerDtos = dto.getPlayers();
            List<Player> players = new ArrayList<>();
            for (Integer playerDto : playerDtos) {
                players.add(playerService.findById(playerDto));
            }
            team.setPlayers(players);
        }
        return team;
    }
}
