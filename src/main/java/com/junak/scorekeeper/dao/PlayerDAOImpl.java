package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Player;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    private EntityManager entityManager;

    @Autowired
    public PlayerDAOImpl(EntityManager entityManager){
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

    @Override
    public List<Player> findAll() {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query
        Query<Player> theQuery =
                currentSession.createQuery("from Player", Player.class);

        // execute query and get result list
        List<Player> players = theQuery.getResultList();

        // return the results
        return players;
    }

    @Override
    public Player findById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // get the employee
        Player player =
                currentSession.get(Player.class, id);

        // return the employee
        return player;
    }

    @Override
    public void save(Player player) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // save employee
        currentSession.saveOrUpdate(player);
    }

    @Override
    public void deleteById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // delete object with primary key
        Query theQuery =
                currentSession.createQuery(
                        "delete from Player where id=:playerId");
        theQuery.setParameter("playerId", id);

        theQuery.executeUpdate();
    }
}
