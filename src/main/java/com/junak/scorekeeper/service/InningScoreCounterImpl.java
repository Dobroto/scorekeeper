//package com.junak.scorekeeper.service;
//
//import com.junak.scorekeeper.entity.Team;
//
//public class InningScoreCounterImpl implements InningScoreCounter {
//
//    private Team team;
//
//    private int runs = 0;
//
//    private int outs = 0;
//
//    public InningScoreCounterImpl() {
//
//    }
//
//    @Override
//    public void setTeam(Team team) {
//        this.team = team;
//    }
//
//    @Override
//    public void incrementRun() {
//        runs ++;
//    }
//
//    public int getRuns() {
//        return runs;
//    }
//
//    @Override
//    public void incrementOut() {
//        outs ++;
//        if(outs == 3) {
//            resetScore();
//        }
//    }
//
//    public int getOuts() {
//        return outs;
//    }
//
//    @Override
//    public void resetScore() {
//        runs = 0;
//        outs = 0;
//    }
//}
