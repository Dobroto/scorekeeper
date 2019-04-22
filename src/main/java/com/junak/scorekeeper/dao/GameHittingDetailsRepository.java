package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.GameHittingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameHittingDetailsRepository extends JpaRepository<GameHittingDetails, Integer> {
}
