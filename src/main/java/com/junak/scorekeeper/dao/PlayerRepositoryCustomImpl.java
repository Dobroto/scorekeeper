package com.junak.scorekeeper.dao;

import com.junak.scorekeeper.Constants;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.entity.Team;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {

    private EntityManager entityManager;

    @Autowired
    public PlayerRepositoryCustomImpl(EntityManager entityManager) {
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
    public Player getFirstBaseRunner(Team team) {
        Session currentSession = entityManager.unwrap(Session.class);

        int teamId = team.getId();
        Query<Player> theQuery =
                currentSession.createQuery("from Player where offense_position=:runnerFirstBase and team_id=:teamId");
        theQuery.setParameter("runnerFirstBase", Constants.runnerFirstBase);
        theQuery.setParameter("teamId", teamId);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Player getSecondBaseRunner(Team team) {
        Session currentSession = entityManager.unwrap(Session.class);

        int teamId = team.getId();
        Query<Player> theQuery =
                currentSession.createQuery("from Player where offense_position=:runnerSecondBase and team_id=:teamId");
        theQuery.setParameter("runnerSecondBase", Constants.runnerSecondBase);
        theQuery.setParameter("teamId", teamId);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Player getThirdBaseRunner(Team team) {
        Session currentSession = entityManager.unwrap(Session.class);

        int teamId = team.getId();
        Query<Player> theQuery =
                currentSession.createQuery("from Player where offense_position=:runnerThirdBase and team_id=:teamId");
        theQuery.setParameter("runnerThirdBase", Constants.runnerThirdBase);
        theQuery.setParameter("teamId", teamId);
        try {
            return theQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Player getStartingBatter(Team team) {
        Session currentSession = entityManager.unwrap(Session.class);

        int teamId = team.getId();
        Query<Player> theQuery =
                currentSession.createQuery("from Player where batting_order=:battingOrder and team_id=:teamId");
        theQuery.setParameter("battingOrder", 1);
        theQuery.setParameter("teamId", teamId);
        return theQuery.getSingleResult();
    }

    @Override
    public Player getNextBatter(Player currentBatter) {
        Session currentSession = entityManager.unwrap(Session.class);

        int nextBattingNumber = currentBatter.getBattingOrder() + 1;
        if(nextBattingNumber > 9){
            nextBattingNumber = 1;
        }

        int teamId = currentBatter.getTeam().getId();
        Query<Player> theQuery =
                currentSession.createQuery("from Player where batting_order=:battingOrder and team_id=:teamId");
        theQuery.setParameter("battingOrder", nextBattingNumber);
        theQuery.setParameter("teamId", teamId);
        return theQuery.getSingleResult();
    }
}
