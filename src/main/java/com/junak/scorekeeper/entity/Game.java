package com.junak.scorekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "game")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Game {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @Column(name = "date")
//    @Temporal(TemporalType.DATE)
//    private Date dateOfGame;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "home_team_id")
//    private Team homeTeam;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "visitor_team_id")
//    private Team visitorTeam;
//
//    @OneToMany(mappedBy = "game",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerHittingDetails> homeTeamHittingDetails;
//
//    @OneToMany(mappedBy = "game",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerHittingDetails> visitorTeamHittingDetails;
//
//    @OneToMany(mappedBy = "game",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerPitchingDetails> homeTeamPitchingDetails;
//
//    @OneToMany(mappedBy = "game",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerPitchingDetails> visitorTeamPitchingDetails;
//
//    @OneToMany(mappedBy = "game",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerFieldingDetails> homeTeamFieldingDetails;
//
//    @OneToMany(mappedBy = "game",
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PlayerFieldingDetails> visitorTeamFieldingDetails;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "win_pitcher_player_id")
//    private Player winPitcher;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "lose_pitcher_player_id")
//    private Player losePitcher;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "save_pitcher_player_id")
//    private Player savePitcher;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "blown_save_pitcher_player_id")
//    private Player blownSavePitcher;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "hold_pitcher_player_id")
//    private Player holdPitcher;

}
