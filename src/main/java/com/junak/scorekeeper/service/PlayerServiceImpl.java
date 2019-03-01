package com.junak.scorekeeper.service;

import com.junak.scorekeeper.dao.PlayerDAO;
import com.junak.scorekeeper.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{
    private PlayerDAO playerDAO;

    @Autowired
    public PlayerServiceImpl(PlayerDAO thePlayerDAO) {
        playerDAO = thePlayerDAO;
    }

    @Override
    @Transactional
    public List<Player> findAllTeamPlayers(int teamId) {
        return playerDAO.findAllTeamPlayers(teamId);
    }

    @Override
    @Transactional
    public List<Player> findAll() {
        return playerDAO.findAll();
    }

    @Override
    @Transactional
    public Player findById(int theId) {
        return playerDAO.findById(theId);
    }

    @Override
    @Transactional
    public void save(Player thePlayer) {
        playerDAO.save(thePlayer);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        playerDAO.deleteById(theId);
    }

}
