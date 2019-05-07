package com.junak.scorekeeper.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;

@Repository
public class GameRepositoryCustomImpl implements GameRepositoryCustom {

    private EntityManager entityManager;

    @Autowired
    public GameRepositoryCustomImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

//    @Override
//    public void setStartTime(int gameId) {
//        Session currentSession = entityManager.unwrap(Session.class);
//
//        Date currentDate = new Date();
//        Query query = currentSession.createQuery("update Game set date = :currentDate" +
//                " where id = :gameId");
//        query.setParameter("currentDate", currentDate);
//        query.setParameter("gameId", gameId);
//        query.executeUpdate();
//    }
}
