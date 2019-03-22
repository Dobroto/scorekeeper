package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.PlayerHittingDetails;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PlayerHittingDetailsCustomImpl implements PlayerHittingDetailsCustom {

    private EntityManager entityManager;

    @Autowired
    public PlayerHittingDetailsCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PlayerHittingDetails getPlayerHittingDetails(int playerId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("from PlayerHittingDetails where player_id=:playerId");
        theQuery.setParameter("playerId", playerId);

        PlayerHittingDetails details = (PlayerHittingDetails)theQuery.getSingleResult();

        return details;
    }
}
