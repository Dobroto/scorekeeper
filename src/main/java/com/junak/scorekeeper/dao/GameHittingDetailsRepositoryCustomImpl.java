package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GameHittingDetails;
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
public class GameHittingDetailsRepositoryCustomImpl implements GameHittingDetailsRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(GameHittingDetailsRepositoryCustomImpl.class);
    private EntityManager entityManager;

    @Autowired
    public GameHittingDetailsRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GameHittingDetails getGameHittingDetails(Player batter, Game game) {
        Session currentSession = entityManager.unwrap(Session.class);

        int playerId = batter.getId();
        int gameId = game.getId();
        Query<GameHittingDetails> theQuery =
                currentSession.createQuery("from GameHittingDetails where player_id=:playerId and game_id=:gameId");
        theQuery.setParameter("playerId", playerId);
        theQuery.setParameter("gameId", gameId);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("There is no game hitting details of player with id {} and game with id {}.", playerId, gameId);
            return null;
        }
    }
}
