package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.entity.Team;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TeamDAOImpl implements TeamDAO {

    private EntityManager entityManager;

    @Autowired
    public TeamDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Team> findAll() {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query
        Query<Team> theQuery =
                currentSession.createQuery("from Team", Team.class);

        // execute query and get result list
        List<Team> teams = theQuery.getResultList();

        // return the results
        return teams;
    }

    @Override
    public Team findById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // get the team
        Team team =
                currentSession.get(Team.class, id);

        // return the team
        return team;
    }

    @Override
    public void save(Team team) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // save employee
        currentSession.saveOrUpdate(team);
    }

    @Override
    public void deleteById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // delete object with primary key
        Query theQuery =
                currentSession.createQuery(
                        "delete from Team where id=:teamId");
        theQuery.setParameter("teamId", id);

        theQuery.executeUpdate();
    }
}
