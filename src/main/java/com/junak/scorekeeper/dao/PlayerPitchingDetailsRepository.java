package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.PlayerPitchingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerPitchingDetailsRepository extends JpaRepository<PlayerPitchingDetails, Integer> {
}
