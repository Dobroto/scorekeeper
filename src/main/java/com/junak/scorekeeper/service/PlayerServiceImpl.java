package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.PlayerRepository;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> findAllTeamPlayers(int teamId) {
        return playerRepository.findAllTeamPlayers(teamId);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(int theId) {
        Optional<Player> result = playerRepository.findById(theId);

        Player thePlayer = null;

        if (result.isPresent()) {
            thePlayer = result.get();
        } else {
            throw new GameNotFoundException("Player id not found - " + theId);
        }

        return thePlayer;
    }

    @Override
    public void save(Player thePlayer) {
        playerRepository.save(thePlayer);
    }

    @Override
    public void deleteById(int theId) {
        playerRepository.deleteById(theId);
    }

    @Override
    public Player getFirstBaseRunner(Team team) {
        return playerRepository.getFirstBaseRunner(team);
    }

    @Override
    public Player getSecondBaseRunner(Team team) {
        return playerRepository.getSecondBaseRunner(team);
    }

    @Override
    public Player getThirdBaseRunner(Team team) {
        return playerRepository.getThirdBaseRunner(team);
    }

    @Override
    public Player getStartingBatter(Team team) {
        return playerRepository.getStartingBatter(team);
    }

    @Override
    public Player getNextBatter(Player currentBatter) {
        return playerRepository.getNextBatter(currentBatter);
    }

}
