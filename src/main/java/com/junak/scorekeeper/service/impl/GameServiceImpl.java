package com.junak.scorekeeper.service.impl;

import com.junak.scorekeeper.dao.GameRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;

    @Autowired
    public GameServiceImpl (GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(int id) {
        Optional<Game> result = gameRepository.findById(id);

        Game theGame = null;

        if (result.isPresent()) {
            theGame = result.get();
        } else {
            throw new GameNotFoundException("Game id not found - " + id);
        }

        return theGame;
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteById(int id) {
        gameRepository.deleteById(id);
    }

//    @Override
//    public void startGame(int gameId) {
//        gameRepository.setStartTime(gameId);
//    }

}
