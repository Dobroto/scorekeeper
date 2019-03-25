package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.PlayerPitchingDetails;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PlayerPitchingDetailsCustomImpl implements PlayerPitchingDetailsCustom {
    private EntityManager entityManager;

    @Autowired
    public PlayerPitchingDetailsCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PlayerPitchingDetails getPlayerPitchingDetails(int playerId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("from PlayerPitchingDetails where player_id=:playerId");
        theQuery.setParameter("playerId", playerId);

        PlayerPitchingDetails details = (PlayerPitchingDetails)theQuery.getSingleResult();

        return details;
    }
}
