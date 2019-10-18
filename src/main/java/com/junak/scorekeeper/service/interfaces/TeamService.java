package com.junak.scorekeeper.service.interfaces;

import com.junak.scorekeeper.entity.Team;

import java.util.List;

public interface TeamService {
    List<Team> findAll();

    Team findById(int id);

    Team save(Team team);

    void deleteById(int id);
}
