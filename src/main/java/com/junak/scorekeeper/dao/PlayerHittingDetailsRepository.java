package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.PlayerHittingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerHittingDetailsRepository extends JpaRepository<PlayerHittingDetails, Integer> {
}
