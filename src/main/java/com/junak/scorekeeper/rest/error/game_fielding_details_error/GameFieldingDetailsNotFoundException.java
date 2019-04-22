package com.junak.scorekeeper.rest.error.game_fielding_details_error;

public class GameFieldingDetailsNotFoundException extends RuntimeException{
    public GameFieldingDetailsNotFoundException() {

    }

    public GameFieldingDetailsNotFoundException (String mesage){
        super(mesage);
    }

    public GameFieldingDetailsNotFoundException (Throwable cause){
        super(cause);
    }

    public GameFieldingDetailsNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public GameFieldingDetailsNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                                   boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
