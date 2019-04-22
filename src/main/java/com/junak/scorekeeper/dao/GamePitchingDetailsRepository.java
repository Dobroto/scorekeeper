package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.GamePitchingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePitchingDetailsRepository extends JpaRepository<GamePitchingDetails, Integer> {
}
