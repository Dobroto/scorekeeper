package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
