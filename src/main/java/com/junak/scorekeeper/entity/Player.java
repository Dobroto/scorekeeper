package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.*;

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

    @Column(name = "defence_position")
    private String defencePosition;

    @Column(name = "offense_position")
    private String offensePosition;

    @Column(name = "batting_order")
    private int battingOrder;

    //number of balls thrown by a pitcher outside of the strike zone
    @Column(name = "ball_count")
    private int ballCount;

    @Column(name = "strike_count")
    private int strikeCount;

    @Column(name = "was_pitcher")
    private boolean wasPitcher;

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

    public static Player fromId(int playerId) {
        Player player = new Player();
        player.id = playerId;
        return player;
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

    public String getDefencePosition() {
        return defencePosition;
    }

    public void setDefencePosition(String defencePosition) {
        this.defencePosition = defencePosition;
    }

    public int getBattingOrder() {
        return battingOrder;
    }

    public void setBattingOrder(int battingOrder) {
        this.battingOrder = battingOrder;
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public void setStrikeCount(int strikeCount) {
        this.strikeCount = strikeCount;
    }

    public boolean isWasPitcher() {
        return wasPitcher;
    }

    public void setWasPitcher(boolean wasPitcher) {
        this.wasPitcher = wasPitcher;
    }

    public String getOffensePosition() {
        return offensePosition;
    }

    public void setOffensePosition(String offensePosition) {
        this.offensePosition = offensePosition;
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
