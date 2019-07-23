package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name = "player_fielding_details")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PlayerFieldingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "GS")
    private int gamesStarted;

    @Column(name = "G")
    private int gamesPlayed;

    @Column(name = "INN")
    private double innings;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "TC")
    private int totalChances;

    @Column(name = "PO")
    private int putOut;

    @Column(name = "A")
    private int assists;

    @Column(name = "E")
    private int errors;

    @Column(name = "DP")
    private int doublePlays;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "FPCT")
    private Double averageOfErrorsPerTotalChances;

    @OneToOne(mappedBy = "playerFieldingDetails",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Player player;

    public PlayerFieldingDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGamesStarted() {
        return gamesStarted;
    }

    public void setGamesStarted(int gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getInnings() {
        return innings;
    }

    public void setInnings(double innings) {
        this.innings = innings;
    }

    public int getTotalChances() {
        return totalChances;
    }

    public int getPutOut() {
        return putOut;
    }

    public void setPutOut(int putOut) {
        this.putOut = putOut;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getDoublePlays() {
        return doublePlays;
    }

    public void setDoublePlays(int doublePlays) {
        this.doublePlays = doublePlays;
    }

    public Double getAverageOfErrorsPerTotalChances() {
        return averageOfErrorsPerTotalChances;
    }
}
