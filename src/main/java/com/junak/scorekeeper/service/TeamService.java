package com.junak.scorekeeper.service;

import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface TeamService {
    public List<Team> findAll();

    public Team findById(int id);

    public void save(Team team);

    public void deleteById(int id);
}
