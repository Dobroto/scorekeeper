package com.junak.scorekeeper.rest.error.game_error;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException() {

    }

    public GameNotFoundException (String mesage){
        super(mesage);
    }

    public GameNotFoundException (Throwable cause){
        super(cause);
    }

    public GameNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public GameNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
