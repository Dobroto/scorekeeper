package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameFieldingDetails;
import com.junak.scorekeeper.entity.Player;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class GameFieldingDetailsRepositoryCustomImpl implements GameFieldingDetailsRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(GameFieldingDetailsRepositoryCustomImpl.class);
    private EntityManager entityManager;

    @Autowired
    public GameFieldingDetailsRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GameFieldingDetails getGameFieldingDetails(Player fielder, Game game) {
        Session currentSession = entityManager.unwrap(Session.class);

        int playerId = fielder.getId();
        int gameId = game.getId();
        Query<GameFieldingDetails> theQuery =
                currentSession.createQuery("from GameFieldingDetails where player_id=:playerId and game_id=:gameId");
        theQuery.setParameter("playerId", playerId);
        theQuery.setParameter("gameId", gameId);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("There is no game fielding details of player with id {} and game with id {}.", playerId, gameId);
            return null;
        }
    }
}
