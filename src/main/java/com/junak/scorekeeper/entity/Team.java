package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "team_name_long")
    private String teamNameLong;

    @Column(name = "team_name_short")
    private String teamNameShort;

    @Column(name = "is_attacking")
    private Boolean isAttacking;

    @OneToMany(mappedBy = "team",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Player> players;

    @OneToMany(mappedBy = "homeTeam",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> homeGames;

    @OneToMany(mappedBy = "visitorTeam",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Game> visitorGames;

    public Team() {

    }

    public Team(String teamName) {
        this.teamNameLong = teamName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamNameLong() {
        return teamNameLong;
    }

    public void setTeamNameLong(String teamName) {
        this.teamNameLong = teamName;
    }

    public String getTeamNameShort() {
        return teamNameShort;
    }

    public void setTeamNameShort(String teamNameShort) {
        this.teamNameShort = teamNameShort;
    }

    public Boolean getAttacking() {
        return isAttacking;
    }

    public void setAttacking(Boolean attacking) {
        isAttacking = attacking;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void add(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
        player.setTeam(this);
    }

    public List<Game> getHomeGames() {
        return homeGames;
    }

    public void setHomeGames(List<Game> homeGames) {
        this.homeGames = homeGames;
    }

    public List<Game> getVisitorGames() {
        return visitorGames;
    }

    public void setVisitorGames(List<Game> visitorGames) {
        this.visitorGames = visitorGames;
    }
}
