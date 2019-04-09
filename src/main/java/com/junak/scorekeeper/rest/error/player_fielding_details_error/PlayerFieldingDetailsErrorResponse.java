package com.junak.scorekeeper.rest.error.player_fielding_details_error;

public class PlayerFieldingDetailsErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public PlayerFieldingDetailsErrorResponse(){

    }

    public PlayerFieldingDetailsErrorResponse(int status, String message, long timeStamp){
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
