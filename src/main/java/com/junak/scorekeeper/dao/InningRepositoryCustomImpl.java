package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Inning;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class InningRepositoryCustomImpl implements InningRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(InningRepositoryCustomImpl.class);
    private EntityManager entityManager;

    @Autowired
    public InningRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Inning getCurrentInning(Game game) {
        Session currentSession = entityManager.unwrap(Session.class);

        //TODO find correct sql query
        int gameId = game.getId();
//        Query<Inning> theQuery =
//                currentSession.createQuery
//                        ("from Inning where inningNumber = (select max(inningNumber) from Inning where game_id=:gameId )");
        Query<Inning> theQuery =
                currentSession.createQuery
                        ("from Inning where game_id=:gameId");
        theQuery.setParameter("gameId", gameId);

        try {
            List<Inning> innings = theQuery.getResultList();
            return innings.get(innings.size() - 1);
        } catch (NoResultException ex) {
            logger.info("There is no inning for this game.");
            return null;
        }
    }
}
