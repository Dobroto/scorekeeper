package com.junak.scorekeeper.dto;

public class PlayerDto {
    private int id;

    private String firstName;

    private String lastName;

    private int jerseyNumber;

    private boolean starter;

    private String defencePosition;

    private String offencePosition;

    private int battingOrder;

    private int ballCount;

    private int strikeCount;

    private int playerHittingDetails;

    private int playerPitchingDetails;

    private int playerFieldingDetails;

    private int team;

    public PlayerDto() {
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

    public String getDefencePosition() {
        return defencePosition;
    }

    public void setDefencePosition(String defencePosition) {
        this.defencePosition = defencePosition;
    }

    public String getOffencePosition() {
        return offencePosition;
    }

    public void setOffencePosition(String offencePosition) {
        this.offencePosition = offencePosition;
    }

    public int getBattingOrder() {
        return battingOrder;
    }

    public void setBattingOrder(int battingOrder) {
        this.battingOrder = battingOrder;
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public void setStrikeCount(int strikeCount) {
        this.strikeCount = strikeCount;
    }

    public int getPlayerHittingDetails() {
        return playerHittingDetails;
    }

    public void setPlayerHittingDetails(int playerHittingDetails) {
        this.playerHittingDetails = playerHittingDetails;
    }

    public int getPlayerPitchingDetails() {
        return playerPitchingDetails;
    }

    public void setPlayerPitchingDetails(int playerPitchingDetails) {
        this.playerPitchingDetails = playerPitchingDetails;
    }

    public int getPlayerFieldingDetails() {
        return playerFieldingDetails;
    }

    public void setPlayerFieldingDetails(int playerFieldingDetails) {
        this.playerFieldingDetails = playerFieldingDetails;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
}
