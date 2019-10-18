package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> , PlayerRepositoryCustom {
}
