package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.FinalResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalResultRepository extends JpaRepository<FinalResult, Integer> {
}
