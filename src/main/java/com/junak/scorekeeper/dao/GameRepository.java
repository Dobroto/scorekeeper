package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer>, GameRepositoryCustom {
}
