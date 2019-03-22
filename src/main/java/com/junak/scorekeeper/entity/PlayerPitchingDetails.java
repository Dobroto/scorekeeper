package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "player_pitching_details")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PlayerPitchingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "W")
    private int wins;

    @Column(name = "L")
    private int loses;

    @Column(name = "ER")
    private int earnedRuns;

    @Column(name = "IP")
    private double inningsPitched;

    @Column(name = "ERA")
    private double earnedRunAverage;

    @Column(name = "G")
    private int games;

    @Column(name = "GS")
    private int gamesStarted;

    @Column(name = "SV")
    private int saves;

    @Column(name = "SVO")
    private int saveOpportunities;

    @Column(name = "H")
    private int hits;

    @Column(name = "R")
    private int runs;

    @Column(name = "HR")
    private int homeRuns;

    @Column(name = "BB")
    private int basesOnBalls;

    @Column(name = "SO")
    private int strikeOuts;

    @Column(name = "AVG")
    private double average;

    @Column(name = "WHIP")
    private double whips;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "player_id")
    private Player player;

    public PlayerPitchingDetails() {

    }


}
