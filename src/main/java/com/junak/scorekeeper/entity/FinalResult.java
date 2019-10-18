package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "final_result")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class FinalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "visitor_team_score")
    private int visitorTeamScore;

    @Column(name = "home_team_score")
    private int homeTeamScore;

    @Column(name = "visitor_team_hits")
    private int visitorTeamHits;

    @Column(name = "home_team_hits")
    private int homeTeamHits;

    @Column(name = "visitor_team_errors")
    private int visitorTeamErrors;

    @Column(name = "home_team_errors")
    private int homeTeamErrors;

    @OneToOne(mappedBy = "finalResult",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private Game game;

    public FinalResult() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVisitorTeamHits() {
        return visitorTeamHits;
    }

    public void setVisitorTeamHits(int visitorTeamHits) {
        this.visitorTeamHits = visitorTeamHits;
    }

    public int getHomeTeamHits() {
        return homeTeamHits;
    }

    public void setHomeTeamHits(int homeTeamHits) {
        this.homeTeamHits = homeTeamHits;
    }

    public int getVisitorTeamErrors() {
        return visitorTeamErrors;
    }

    public void setVisitorTeamErrors(int visitorTeamErrors) {
        this.visitorTeamErrors = visitorTeamErrors;
    }

    public int getHomeTeamErrors() {
        return homeTeamErrors;
    }

    public void setHomeTeamErrors(int homeTeamErrors) {
        this.homeTeamErrors = homeTeamErrors;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
