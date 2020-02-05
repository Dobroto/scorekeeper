package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import com.junak.scorekeeper.entity.Play;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class PlayRepositoryCustomImpl implements PlayRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(PlayRepositoryCustomImpl.class);

    private EntityManager entityManager;

    @Autowired
    public PlayRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Play> getAllPlaysInInning(Inning inning) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Play> theQuery =
                currentSession.createQuery("from Play where inning_id=:inningId");
        theQuery.setParameter("inningId", inning.getId());

        return theQuery.getResultList();
    }

    @Override
    public List<Play> getAllPlaysInGame(Game game) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Play> theQuery =
                currentSession.createQuery("from Play where game_id=:gameId");
        theQuery.setParameter("gameId", game.getId());

        return theQuery.getResultList();
    }
}
