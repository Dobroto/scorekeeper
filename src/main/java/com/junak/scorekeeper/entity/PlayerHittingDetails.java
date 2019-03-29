package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "player_hitting_details")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PlayerHittingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "G")
    private int games;

    @Column(name = "PA")
    private int plateAppearences;

    @Column(name = "SH")
    private int sacrificeHits;

    @Column(name = "BB")
    private int baseForBalls;

    @Column(name = "HBP")
    private int hitByPitches;

    @Column(name = "AB")
    private int atBat;

    @Column(name = "R")
    private int runs;

    @Column(name = "H")
    private int hits;

    @Column(name = "2B")
    private int doubleHit;

    @Column(name = "3B")
    private int trippleHit;

    @Column(name = "HR")
    private int homeRun;

    @Column(name = "RBI")
    private int runBattedIn;

    @Column(name = "SO")
    private int strikeOut;

    @Column(name = "SB")
    private int stolenBase;

    @Column(name = "CS")
    private int caughtStealing;

    @Column(name = "SF")
    private int sacrificeFlies;

    @Column(name = "TB")
    private int totalBases;

    @Column(name = "AVG")
    private double battingAverage;

    @Column(name = "OBP")
    private double onBasePercantage;

    @Column(name = "SLG")
    private double sluggingPercentage;

    @Column(name = "OPS")
    private double onBaseSlugging;

    @OneToOne(mappedBy="playerHittingDetails",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private Player player;

    public PlayerHittingDetails(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getPlateAppearences() {
        return plateAppearences;
    }

    public void setPlateAppearences(int plateAppearences) {
        this.plateAppearences = plateAppearences;
    }

    public int getSacrificeHits() {
        return sacrificeHits;
    }

    public void setSacrificeHits(int sacrificeHits) {
        this.sacrificeHits = sacrificeHits;
    }

    public int getBaseForBalls() {
        return baseForBalls;
    }

    public void setBaseForBalls(int baseForBalls) {
        this.baseForBalls = baseForBalls;
    }

    public int getHitByPitches() {
        return hitByPitches;
    }

    public void setHitByPitches(int hitByPitches) {
        this.hitByPitches = hitByPitches;
    }

    public int getAtBat() {
        return atBat;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getDoubleHit() {
        return doubleHit;
    }

    public void setDoubleHit(int doubleHit) {
        this.doubleHit = doubleHit;
    }

    public int getTrippleHit() {
        return trippleHit;
    }

    public void setTrippleHit(int trippleHit) {
        this.trippleHit = trippleHit;
    }

    public int getHomeRun() {
        return homeRun;
    }

    public void setHomeRun(int homeRun) {
        this.homeRun = homeRun;
    }

    public int getRunBattedIn() {
        return runBattedIn;
    }

    public void setRunBattedIn(int runBattedIn) {
        this.runBattedIn = runBattedIn;
    }

    public int getStrikeOut() {
        return strikeOut;
    }

    public void setStrikeOut(int strikeOut) {
        this.strikeOut = strikeOut;
    }

    public int getStolenBase() {
        return stolenBase;
    }

    public void setStolenBase(int stolenBase) {
        this.stolenBase = stolenBase;
    }

    public int getCaughtStealing() {
        return caughtStealing;
    }

    public void setCaughtStealing(int caughtStealing) {
        this.caughtStealing = caughtStealing;
    }

    public int getSacrificeFlies() {
        return sacrificeFlies;
    }

    public void setSacrificeFlies(int sacrificeFlies) {
        this.sacrificeFlies = sacrificeFlies;
    }

    public int getTotalBases() {
        return totalBases;
    }

    public double getBattingAverage() {
        return battingAverage;
    }

    public double getOnBasePercantage() {
        return onBasePercantage;
    }

    public double getSluggingPercentage() {
        return sluggingPercentage;
    }

    public double getOnBaseSlugging() {
        return onBaseSlugging;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
