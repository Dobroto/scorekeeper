package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {

    private EntityManager entityManager;

    @Autowired
    public PlayerRepositoryCustomImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Player> findAllTeamPlayers(int teamId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Player> theQuery =
                currentSession.createQuery("from Player where team_id=:teamId");
        theQuery.setParameter("teamId", teamId);

        List<Player> players = theQuery.getResultList();

        return players;
    }
}
