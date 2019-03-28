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

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="player_pitching_details")
    private PlayerPitchingDetails pitchingDetails;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="player_hitting_details")
    private PlayerHittingDetails hittingDetails;

//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="player_fielding_details")
//    private PlayerFieldingDetails fieldingDetails;

//    @OneToMany(mappedBy = "player",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerFieldingDetails> homeTeamFieldingDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {

    }

    public Player(String firstName, String lastName, int jerseyNumber, boolean starter, String position, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.starter = starter;
        this.position = position;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public PlayerPitchingDetails getPitchingDetails() {
        return pitchingDetails;
    }

    public void setPitchingDetails(PlayerPitchingDetails pitchingDetails) {
        this.pitchingDetails = pitchingDetails;
    }

    public PlayerHittingDetails getHittingDetails() {
        return hittingDetails;
    }

    public void setHittingDetails(PlayerHittingDetails hittingDetails) {
        this.hittingDetails = hittingDetails;
    }

//    public PlayerFieldingDetails getFieldingDetails() {
//        return fieldingDetails;
//    }
//
//    public void setFieldingDetails(PlayerFieldingDetails fieldingDetails) {
//        this.fieldingDetails = fieldingDetails;
//    }

//    public List<PlayerFieldingDetails> getHomeTeamFieldingDetails() {
//        return homeTeamFieldingDetails;
//    }
//
//    public void setHomeTeamFieldingDetails(List<PlayerFieldingDetails> homeTeamFieldingDetails) {
//        this.homeTeamFieldingDetails = homeTeamFieldingDetails;
//    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jerseyNumber=" + jerseyNumber +
                ", starter=" + starter +
                ", position='" + position + '\'' +
                ", team=" + team +
                '}';
    }
}
