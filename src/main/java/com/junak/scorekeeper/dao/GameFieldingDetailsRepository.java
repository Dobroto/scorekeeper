package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.GameFieldingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameFieldingDetailsRepository extends JpaRepository<GameFieldingDetails, Integer> {
}
