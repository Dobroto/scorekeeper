package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;
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
public class GamePitchingDetailsRepositoryCustomImpl implements GamePitchingDetailsRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(GamePitchingDetailsRepositoryCustomImpl.class);
    private EntityManager entityManager;

    @Autowired
    public GamePitchingDetailsRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GamePitchingDetails getGamePitchingDetails(Player pitcher, Game game) {
        Session currentSession = entityManager.unwrap(Session.class);

        int playerId = pitcher.getId();
        int gameId = game.getId();
        Query<GamePitchingDetails> theQuery =
                currentSession.createQuery("from GamePitchingDetails where player_id=:playerId and game_id=:gameId");
        theQuery.setParameter("playerId", playerId);
        theQuery.setParameter("gameId", gameId);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("There is no game pitching details of player with id {} and game with id {}.", playerId, gameId);
            return null;
        }
    }

    @Override
    public List<GamePitchingDetails> getGamePitchingDetailsList(Game game) {
        Session currentSession = entityManager.unwrap(Session.class);

        int gameId = game.getId();
        Query<GamePitchingDetails> theQuery =
                currentSession.createQuery("from GamePitchingDetails where game_id=:gameId");
        theQuery.setParameter("gameId", gameId);
        try {
            return theQuery.getResultList();
        } catch (NoResultException ex) {
            logger.info("There is no game pitching details of game with id {}.", gameId);
            return null;
        }
    }

    @Override
    public List<GamePitchingDetails> getGamePitchingDetailsList(Player player) {
        Session currentSession = entityManager.unwrap(Session.class);

        int playerId = player.getId();
        Query<GamePitchingDetails> theQuery =
                currentSession.createQuery("from GamePitchingDetails where player_id=:playerId");
        theQuery.setParameter("playerId", playerId);
        try {
            return theQuery.getResultList();
        } catch (NoResultException ex) {
            logger.info("There is no game pitching details of player with id {}.", playerId);
            return null;
        }
    }
}
