package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.TeamRepository;
import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.rest.error.team_error.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl (TeamRepository teamRepository){
        this.teamRepository = teamRepository;
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
            throw new TeamNotFoundException("Team id not found - " + id);
        }

        return theTeam;
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void deleteById(int id) {
        teamRepository.deleteById(id);
    }
}
