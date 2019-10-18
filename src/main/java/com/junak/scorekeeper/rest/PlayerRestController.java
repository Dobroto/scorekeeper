package com.junak.scorekeeper.rest;

import com.junak.scorekeeper.dto.PlayerDto;
import com.junak.scorekeeper.entity.*;
import com.junak.scorekeeper.rest.exceptions.GameNotFoundException;
import com.junak.scorekeeper.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerRestController {

    private PlayerService playerService;
    private TeamService teamService;
    private PlayerHittingDetailsService playerHittingDetailsService;
    private PlayerPitchingDetailsService playerPitchingDetailsService;
    private PlayerFieldingDetailsService playerFieldingDetailsService;
    private GameHittingDetailsService gameHittingDetailsService;
    private GamePitchingDetailsService gamePitchingDetailsService;
    private GameFieldingDetailsService gameFieldingDetailsService;

    @Autowired
    public PlayerRestController(PlayerService playerService, TeamService teamService,
                                PlayerHittingDetailsService playerHittingDetailsService,
                                PlayerPitchingDetailsService playerPitchingDetailsService,
                                PlayerFieldingDetailsService playerFieldingDetailsService,
                                GameHittingDetailsService gameHittingDetailsService,
                                GamePitchingDetailsService gamePitchingDetailsService,
                                GameFieldingDetailsService gameFieldingDetailsService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.playerHittingDetailsService = playerHittingDetailsService;
        this.playerPitchingDetailsService = playerPitchingDetailsService;
        this.playerFieldingDetailsService = playerFieldingDetailsService;
        this.gameHittingDetailsService = gameHittingDetailsService;
        this.gamePitchingDetailsService = gamePitchingDetailsService;
        this.gameFieldingDetailsService = gameFieldingDetailsService;
    }

    // expose "/players" and return list of players
    @GetMapping("/players")
    public List<PlayerDto> findAll() {
        List<Player> players = playerService.findAll();
        List<PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : players) {
            playerDtos.add(convertToDto(player));
        }
        return playerDtos;
    }

    @GetMapping("/players/team/{teamId}")
    public List<PlayerDto> findAllTeamPlayers(@PathVariable int teamId) {
        List<Player> players = playerService.findAllTeamPlayers(teamId);

        List<PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : players) {
            playerDtos.add(convertToDto(player));
        }
        return playerDtos;
    }

    // add mapping for GET /players/{playerId}

    @GetMapping("/players/{playerId}")
    public PlayerDto getPlayer(@PathVariable int playerId) {

        Player thePlayer = playerService.findById(playerId);

        if (thePlayer == null) {
            throw new GameNotFoundException("Player id not found - " + playerId);
        }

        return convertToDto(thePlayer);
    }

    // add mapping for POST /players - add new player

    @PostMapping("/players")
    public Player addPlayer(@RequestBody PlayerDto thePlayerDto) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        thePlayerDto.setId(0);

        Player thePlayer = convertToEntity(thePlayerDto);

        PlayerHittingDetails playerHittingDetails = new PlayerHittingDetails();
        playerHittingDetails.setPlayer(thePlayer);
        playerHittingDetailsService.save(playerHittingDetails);

        PlayerPitchingDetails playerPitchingDetails = new PlayerPitchingDetails();
        playerPitchingDetails.setPlayer(thePlayer);
        playerPitchingDetailsService.save(playerPitchingDetails);

        PlayerFieldingDetails playerFieldingDetails = new PlayerFieldingDetails();
        playerFieldingDetails.setPlayer(thePlayer);
        playerFieldingDetailsService.save(playerFieldingDetails);

        thePlayer.setPlayerHittingDetails(playerHittingDetails);
        thePlayer.setPlayerPitchingDetails(playerPitchingDetails);
        thePlayer.setPlayerFieldingDetails(playerFieldingDetails);

        playerService.save(thePlayer);

        return thePlayer;
    }

    // add mapping for PUT /players - update existing player

    @PutMapping("/players")
    public Player updatePlayer(@RequestBody PlayerDto thePlayerDto) {

        Player thePlayer = convertToEntity(thePlayerDto);
        playerService.save(thePlayer);

        return thePlayer;
    }

    // add mapping for DELETE /players/{playerId} - delete player

    @DeleteMapping("/players/{playerId}")
    public String deletePlayer(@PathVariable int playerId) {

        Player tempPlayer = playerService.findById(playerId);

        // throw exception if null
        if (tempPlayer == null) {
            throw new GameNotFoundException("Player id not found - " + playerId);
        }

        List<GameHittingDetails> gameHittingDetailsList =
                gameHittingDetailsService.getGameHittingDetailsList(tempPlayer);
        List<GamePitchingDetails> gamePitchingDetailsList =
                gamePitchingDetailsService.getGamePitchingDetailsList(tempPlayer);
        List<GameFieldingDetails> gameFieldingDetailsList =
                gameFieldingDetailsService.getGameFieldingDetailsList(tempPlayer);

        tempPlayer.setGameHittingDetails(null);
        tempPlayer.setGamePitchingDetails(null);
        tempPlayer.setGameFieldingDetails(null);
        playerService.save(tempPlayer);

        deleteGameHittingDetails(gameHittingDetailsList);
        deleteGamePitchingDetails(gamePitchingDetailsList);
        deleteGameFieldingDetails(gameFieldingDetailsList);

        playerService.deleteById(playerId);

        return "Deleted player id - " + playerId;
    }

    private void deleteGameHittingDetails(List<GameHittingDetails> gameHittingDetailsList) {
        if ((gameHittingDetailsList != null) && (gameHittingDetailsList.size() > 0)) {
            GameHittingDetails[] detailsToDeleteArr = new GameHittingDetails[gameHittingDetailsList.size()];

            for (int i = 0; i < detailsToDeleteArr.length; i++) {
                GameHittingDetails detailsToDelete = gameHittingDetailsList.get(i);
                gameHittingDetailsService.deleteById(detailsToDelete.getId());
            }
        }
    }

    private void deleteGamePitchingDetails(List<GamePitchingDetails> gamePitchingDetailsList) {
        if ((gamePitchingDetailsList != null) && (gamePitchingDetailsList.size() > 0)) {
            GamePitchingDetails[] detailsToDeleteArr = new GamePitchingDetails[gamePitchingDetailsList.size()];

            for (int i = 0; i < detailsToDeleteArr.length; i++) {
                GamePitchingDetails detailsToDelete = gamePitchingDetailsList.get(i);
                gamePitchingDetailsService.deleteById(detailsToDelete.getId());
            }
        }
    }

    private void deleteGameFieldingDetails(List<GameFieldingDetails> gameFieldingDetailsList) {
        if ((gameFieldingDetailsList != null) && (gameFieldingDetailsList.size() > 0)) {
            GameFieldingDetails[] detailsToDeleteArr = new GameFieldingDetails[gameFieldingDetailsList.size()];

            for (int i = 0; i < detailsToDeleteArr.length; i++) {
                GameFieldingDetails detailsToDelete = gameFieldingDetailsList.get(i);
                gameFieldingDetailsService.deleteById(detailsToDelete.getId());
            }
        }
    }

    private PlayerDto convertToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setFirstName(player.getFirstName());
        playerDto.setLastName(player.getLastName());
        playerDto.setJerseyNumber(player.getJerseyNumber());
        playerDto.setStarter(player.isStarter());
        playerDto.setDefencePosition(player.getDefencePosition());
        playerDto.setOffencePosition(player.getOffencePosition());
        playerDto.setBattingOrder(player.getBattingOrder());
        playerDto.setBallCount(player.getBallCount());
        playerDto.setStrikeCount(player.getStrikeCount());
        playerDto.setWasPitcher(player.isWasPitcher());

        if (player.getPlayerHittingDetails() != null) {
            playerDto.setPlayerHittingDetails(player.getPlayerHittingDetails().getId());
        }
        if (player.getPlayerPitchingDetails() != null) {
            playerDto.setPlayerPitchingDetails(player.getPlayerPitchingDetails().getId());
        }
        if (player.getPlayerFieldingDetails() != null) {
            playerDto.setPlayerFieldingDetails(player.getPlayerFieldingDetails().getId());
        }
        if (player.getTeam() != null) {
            playerDto.setTeam(player.getTeam().getId());
        }
        if ((player.getGameHittingDetails() != null) && (player.getGameHittingDetails().size() != 0)) {
            List<Integer> gameHittingDetailsDto = new ArrayList<>();
            List<GameHittingDetails> gameHittingDetailsList = player.getGameHittingDetails();
            for (GameHittingDetails details : gameHittingDetailsList) {
                gameHittingDetailsDto.add(details.getId());
            }
            playerDto.setGameHittingDetails(gameHittingDetailsDto);
        }
        if ((player.getGamePitchingDetails() != null) && (player.getGamePitchingDetails().size() != 0)) {
            List<Integer> gamePitchingDetailsDto = new ArrayList<>();
            List<GamePitchingDetails> gamePitchingDetailsList = player.getGamePitchingDetails();
            for (GamePitchingDetails details : gamePitchingDetailsList) {
                gamePitchingDetailsDto.add(details.getId());
            }
            playerDto.setGamePitchingDetails(gamePitchingDetailsDto);
        }
        if ((player.getGameFieldingDetails() != null) && (player.getGameFieldingDetails().size() != 0)) {
            List<Integer> gameFieldingDetailsDto = new ArrayList<>();
            List<GameFieldingDetails> gameFieldingDetailsList = player.getGameFieldingDetails();
            for (GameFieldingDetails details : gameFieldingDetailsList) {
                gameFieldingDetailsDto.add(details.getId());
            }
            playerDto.setGameFieldingDetails(gameFieldingDetailsDto);
        }

        return playerDto;
    }

    private Player convertToEntity(PlayerDto playerDto) {
        Player player = new Player();

        if (playerDto.getId() != 0) {
            player = playerService.findById(playerDto.getId());
        }

        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        player.setJerseyNumber(playerDto.getJerseyNumber());
        player.setStarter(playerDto.isStarter());
        player.setDefencePosition(playerDto.getDefencePosition());
        player.setOffencePosition(playerDto.getOffencePosition());
        player.setBattingOrder(playerDto.getBattingOrder());
        player.setBallCount(playerDto.getBallCount());
        player.setStrikeCount(playerDto.getStrikeCount());
        player.setWasPitcher(playerDto.isWasPitcher());

        if (playerDto.getTeam() > 0) {
            player.setTeam(teamService.findById(playerDto.getTeam()));
        }
        if (playerDto.getTeam() == -1) {
            player.setTeam(null);
        }
        return player;
    }
}
