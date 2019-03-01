package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface TeamDAO {
    public List<Team> findAll();

    public Team findById(int id);

    public void save(Team team);

    public void deleteById(int id);
}
