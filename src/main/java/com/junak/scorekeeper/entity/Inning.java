package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "inning")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Inning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "inning_number")
    private int inningNumber;

    @Column(name = "runs_visitor_team")
    private int visitorTeamRuns;

    @Column(name = "runs_home_team")
    private int homeTeamRuns;

    @Column(name = "outs")
    private int currentOuts;

    public Inning() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(int inningNumber) {
        this.inningNumber = inningNumber;
    }

    public int getVisitorTeamRuns() {
        return visitorTeamRuns;
    }

    public void setVisitorTeamRuns(int visitorTeamRuns) {
        this.visitorTeamRuns = visitorTeamRuns;
    }

    public int getHomeTeamRuns() {
        return homeTeamRuns;
    }

    public void setHomeTeamRuns(int homeTeamRuns) {
        this.homeTeamRuns = homeTeamRuns;
    }

    public int getCurrentOuts() {
        return currentOuts;
    }

    public void setCurrentOuts(int currentOuts) {
        this.currentOuts = currentOuts;
    }
}
