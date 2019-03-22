package com.junak.scorekeeper.rest.error.player_hitting_details_error;

public class PlayerHittingDetailsNotFoundException extends RuntimeException {
    public PlayerHittingDetailsNotFoundException() {

    }

    public PlayerHittingDetailsNotFoundException (String mesage){
        super(mesage);
    }

    public PlayerHittingDetailsNotFoundException (Throwable cause){
        super(cause);
    }

    public PlayerHittingDetailsNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public PlayerHittingDetailsNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
