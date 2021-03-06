package com.junak.scorekeeper.dto;

public class GameFieldingDetailsDto {
    private int id;

    private double innings;

    private int totalChances;

    private int putOut;

    private int assists;

    private int errors;

    private int doublePlays;

    private double averageOfErrorsPerTotalChances;

    private int player;

    private int game;

    public GameFieldingDetailsDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setTotalChances(int totalChances) {
        this.totalChances = totalChances;
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

    public double getAverageOfErrorsPerTotalChances() {
        return averageOfErrorsPerTotalChances;
    }

    public void setAverageOfErrorsPerTotalChances(double averageOfErrorsPerTotalChances) {
        this.averageOfErrorsPerTotalChances = averageOfErrorsPerTotalChances;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
}
