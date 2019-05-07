package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTimeOfGame;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTimeOfGame;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "visitor_team_id")
    private Team visitorTeam;

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<GameHittingDetails> gameHittingDetails;

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<GamePitchingDetails> gamePitchingDetails;

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<GameFieldingDetails> gameFieldingDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "win_pitcher_player_id")
    private Player winPitcher;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "lose_pitcher_player_id")
    private Player losePitcher;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "save_pitcher_player_id")
    private Player savePitcher;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "blown_save_pitcher_player_id")
    private Player blownSavePitcher;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "hold_pitcher_player_id")
    private Player holdPitcher;

    @Column(name = "last_command")
    public String lastCommand;

    public Game(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTimeOfGame() {
        return startTimeOfGame;
    }

    public void setStartTimeOfGame(Date startTimeOfGame) {
        this.startTimeOfGame = startTimeOfGame;
    }

    public Date getEndTimeOfGame() {
        return endTimeOfGame;
    }

    public void setEndTimeOfGame(Date endTimeOfGame) {
        this.endTimeOfGame = endTimeOfGame;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Player getWinPitcher() {
        return winPitcher;
    }

    public void setWinPitcher(Player winPitcher) {
        this.winPitcher = winPitcher;
    }

    public Player getLosePitcher() {
        return losePitcher;
    }

    public void setLosePitcher(Player losePitcher) {
        this.losePitcher = losePitcher;
    }

    public Player getSavePitcher() {
        return savePitcher;
    }

    public void setSavePitcher(Player savePitcher) {
        this.savePitcher = savePitcher;
    }

    public Player getBlownSavePitcher() {
        return blownSavePitcher;
    }

    public void setBlownSavePitcher(Player blownSavePitcher) {
        this.blownSavePitcher = blownSavePitcher;
    }

    public Player getHoldPitcher() {
        return holdPitcher;
    }

    public void setHoldPitcher(Player holdPitcher) {
        this.holdPitcher = holdPitcher;
    }

    public List<GameHittingDetails> getGameHittingDetails() {
        return gameHittingDetails;
    }

    public void setGameHittingDetails(List<GameHittingDetails> gameHittingDetails) {
        this.gameHittingDetails = gameHittingDetails;
    }

    public List<GamePitchingDetails> getGamePitchingDetails() {
        return gamePitchingDetails;
    }

    public void setGamePitchingDetails(List<GamePitchingDetails> gamePitchingDetails) {
        this.gamePitchingDetails = gamePitchingDetails;
    }

    public List<GameFieldingDetails> getGameFieldingDetails() {
        return gameFieldingDetails;
    }

    public void setGameFieldingDetails(List<GameFieldingDetails> gameFieldingDetails) {
        this.gameFieldingDetails = gameFieldingDetails;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }
}
