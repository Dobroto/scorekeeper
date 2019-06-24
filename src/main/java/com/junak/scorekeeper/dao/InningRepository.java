package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Inning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InningRepository extends JpaRepository<Inning, Integer>, InningRepositoryCustom {
}
