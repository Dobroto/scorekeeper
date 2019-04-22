package com.junak.scorekeeper.rest.error.game_hitting_details_error;

public class GameHittingDetailsNotFoundException extends RuntimeException {
    public GameHittingDetailsNotFoundException() {

    }

    public GameHittingDetailsNotFoundException (String mesage){
        super(mesage);
    }

    public GameHittingDetailsNotFoundException (Throwable cause){
        super(cause);
    }

    public GameHittingDetailsNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public GameHittingDetailsNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
