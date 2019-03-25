package com.junak.scorekeeper.rest.error.player_pitching_details_error;

public class PlayerPitchingDetailsNotFoundException extends RuntimeException {
    public PlayerPitchingDetailsNotFoundException() {

    }

    public PlayerPitchingDetailsNotFoundException(String mesage){
        super(mesage);
    }

    public PlayerPitchingDetailsNotFoundException(Throwable cause){
        super(cause);
    }

    public PlayerPitchingDetailsNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public PlayerPitchingDetailsNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
