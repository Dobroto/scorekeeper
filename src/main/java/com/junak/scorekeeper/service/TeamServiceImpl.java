package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.TeamDAO;
import com.junak.scorekeeper.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private TeamDAO teamDAO;

    @Autowired
    public TeamServiceImpl (TeamDAO teamDAO){
        this.teamDAO = teamDAO;
    }

    @Override
    @Transactional
    public List<Team> findAll() {
        return teamDAO.findAll();
    }

    @Override
    @Transactional
    public Team findById(int id) {
        return teamDAO.findById(id);
    }

    @Override
    @Transactional
    public void save(Team team) {
        teamDAO.save(team);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        teamDAO.deleteById(id);
    }
}
