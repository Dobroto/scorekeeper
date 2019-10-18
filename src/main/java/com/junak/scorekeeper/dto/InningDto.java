package com.junak.scorekeeper.dto;

public class InningDto {
    private int id;

    private int game;

    private int inningNumber;

    private int visitorTeamRuns;

    private int homeTeamRuns;

    private int currentOuts;

    public InningDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
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
