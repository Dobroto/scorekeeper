package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.PlayerFieldingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerFieldingDetailsRepository extends JpaRepository<PlayerFieldingDetails, Integer> {
}
