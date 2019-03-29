package com.junak.scorekeeper.rest.error.player_fielding_details_error;

public class PlayerFieldingDetailsNotFoundException extends RuntimeException{
    public PlayerFieldingDetailsNotFoundException() {

    }

    public PlayerFieldingDetailsNotFoundException (String mesage){
        super(mesage);
    }

    public PlayerFieldingDetailsNotFoundException (Throwable cause){
        super(cause);
    }

    public PlayerFieldingDetailsNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public PlayerFieldingDetailsNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
