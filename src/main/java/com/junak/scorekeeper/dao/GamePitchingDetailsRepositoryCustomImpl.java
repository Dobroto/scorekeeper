package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.GamePitchingDetails;
import com.junak.scorekeeper.entity.Player;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class GamePitchingDetailsRepositoryCustomImpl implements GamePitchingDetailsRepositoryCustom {
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
        return theQuery.getSingleResult();
    }
}
