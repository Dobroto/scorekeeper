package com.junak.scorekeeper.rest.error.game_pitching_details_error;

public class GamePitchingDetailsNotFoundException extends RuntimeException{
    public GamePitchingDetailsNotFoundException() {

    }

    public GamePitchingDetailsNotFoundException(String mesage){
        super(mesage);
    }

    public GamePitchingDetailsNotFoundException(Throwable cause){
        super(cause);
    }

    public GamePitchingDetailsNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public GamePitchingDetailsNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
