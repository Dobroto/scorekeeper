package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Play;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRepository extends JpaRepository<Play, Integer>, PlayRepositoryCustom {
}
