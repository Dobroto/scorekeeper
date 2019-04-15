package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "jersey_number")
    private int jerseyNumber;

    @Column(name = "starter")
    private boolean starter;

    @Column(name = "position")
    private String position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_hitting_details_id")
    private PlayerHittingDetails playerHittingDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_pitching_details_id")
    private PlayerPitchingDetails playerPitchingDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_fielding_details_id")
    private PlayerFieldingDetails playerFieldingDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "winPitcher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> winPitcherGames;

    @OneToMany(mappedBy = "losePitcher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> losePitcherGames;

    @OneToMany(mappedBy = "savePitcher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> savePitcherGames;

    @OneToMany(mappedBy = "blownSavePitcher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> blownSavePitcherGames;

    @OneToMany(mappedBy = "holdPitcher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> holdPitcherGames;

    @OneToMany(mappedBy = "player",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<GameHittingDetails> gameHittingDetails;

    @OneToMany(mappedBy = "player",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<GameFieldingDetails> gameFieldingDetails;

    @OneToMany(mappedBy = "player",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<GamePitchingDetails> gamePitchingDetails;

    public Player() {

    }

    public Player(String firstName, String lastName, int jerseyNumber, boolean starter, String position,
                  PlayerHittingDetails playerHittingDetails, PlayerPitchingDetails playerPitchingDetails,
                  PlayerFieldingDetails playerFieldingDetails, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.starter = starter;
        this.position = position;
        this.playerHittingDetails = playerHittingDetails;
        this.playerPitchingDetails = playerPitchingDetails;
        this.playerFieldingDetails = playerFieldingDetails;
        this.team = team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PlayerHittingDetails getPlayerHittingDetails() {
        return playerHittingDetails;
    }

    public void setPlayerHittingDetails(PlayerHittingDetails playerHittingDetails) {
        this.playerHittingDetails = playerHittingDetails;
    }

    public PlayerPitchingDetails getPlayerPitchingDetails() {
        return playerPitchingDetails;
    }

    public void setPlayerPitchingDetails(PlayerPitchingDetails playerPitchingDetails) {
        this.playerPitchingDetails = playerPitchingDetails;
    }

    public PlayerFieldingDetails getPlayerFieldingDetails() {
        return playerFieldingDetails;
    }

    public void setPlayerFieldingDetails(PlayerFieldingDetails playerFieldingDetails) {
        this.playerFieldingDetails = playerFieldingDetails;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Game> getWinPitcherGames() {
        return winPitcherGames;
    }

    public void setWinPitcherGames(List<Game> winPitcherGames) {
        this.winPitcherGames = winPitcherGames;
    }

    public List<Game> getLosePitcherGames() {
        return losePitcherGames;
    }

    public void setLosePitcherGames(List<Game> losePitcherGames) {
        this.losePitcherGames = losePitcherGames;
    }

    public List<Game> getSavePitcherGames() {
        return savePitcherGames;
    }

    public void setSavePitcherGames(List<Game> savePitcherGames) {
        this.savePitcherGames = savePitcherGames;
    }

    public List<Game> getBlownSavePitcherGames() {
        return blownSavePitcherGames;
    }

    public void setBlownSavePitcherGames(List<Game> blownSavePitcherGames) {
        this.blownSavePitcherGames = blownSavePitcherGames;
    }

    public List<Game> getHoldPitcherGames() {
        return holdPitcherGames;
    }

    public void setHoldPitcherGames(List<Game> holdPitcherGames) {
        this.holdPitcherGames = holdPitcherGames;
    }

    public List<GameHittingDetails> getGameHittingDetails() {
        return gameHittingDetails;
    }

    public void setGameHittingDetails(List<GameHittingDetails> gameHittingDetails) {
        this.gameHittingDetails = gameHittingDetails;
    }

    public List<GameFieldingDetails> getGameFieldingDetails() {
        return gameFieldingDetails;
    }

    public void setGameFieldingDetails(List<GameFieldingDetails> gameFieldingDetails) {
        this.gameFieldingDetails = gameFieldingDetails;
    }

    public List<GamePitchingDetails> getGamePitchingDetails() {
        return gamePitchingDetails;
    }

    public void setGamePitchingDetails(List<GamePitchingDetails> gamePitchingDetails) {
        this.gamePitchingDetails = gamePitchingDetails;
    }
}
