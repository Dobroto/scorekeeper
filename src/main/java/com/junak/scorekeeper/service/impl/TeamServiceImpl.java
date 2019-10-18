package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.GameRepository;
import com.junak.scorekeeper.dao.PlayerRepository;
import com.junak.scorekeeper.dao.TeamRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import com.junak.scorekeeper.service.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;
    private GameRepository gameRepository;

    @Autowired
    public TeamServiceImpl (TeamRepository teamRepository, PlayerRepository playerRepository,
                            GameRepository gameRepository){
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findById(int id) {
        Optional<Team> result = teamRepository.findById(id);

        Team theTeam = null;

        if (result.isPresent()) {
            theTeam = result.get();
        } else {
            throw new GameNotFoundException("Team id not found - " + id);
        }

        return theTeam;
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteById(int id) {
        Optional<Team> result = teamRepository.findById(id);

        Team theTeam = null;

        if (result.isPresent()) {
            theTeam = result.get();
        } else {
            throw new GameNotFoundException("Team id not found - " + id);
        }

        List<Player> players = theTeam.getPlayers();

        Player[] arrPlayers = new Player[players.size()];

        for (int i = 0; i < players.size(); i++) {
            arrPlayers[i] = players.get(i);
        }

        for (Player player : arrPlayers) {
            player.setTeam(null);
            playerRepository.save(player);
        }

        List<Game> homeTeamGames = theTeam.getHomeGames();

        Game[] arrHomeGames = new Game[homeTeamGames.size()];

        for (int i = 0; i < homeTeamGames.size(); i++) {
            arrHomeGames[i] = homeTeamGames.get(i);
        }

        for (Game homeTeamGame : arrHomeGames) {
            homeTeamGame.setHomeTeam(null);
            gameRepository.save(homeTeamGame);
        }

        List<Game> visitorTeamGames = theTeam.getVisitorGames();

        Game[] arrVisitorGames = new Game[visitorTeamGames.size()];

        for (int i = 0; i < visitorTeamGames.size(); i++) {
            arrVisitorGames[i] = visitorTeamGames.get(i);
        }

        for (Game visitorTeamGame : arrVisitorGames) {
            visitorTeamGame.setVisitorTeam(null);
            gameRepository.save(visitorTeamGame);
        }

        teamRepository.deleteById(id);
    }
}
