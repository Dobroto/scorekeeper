package com.junak.scorekeeper.rest.errors;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException() {

    }

    public PlayerNotFoundException (String mesage){
        super(mesage);
    }

    public PlayerNotFoundException (Throwable cause){
        super(cause);
    }

    public PlayerNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public PlayerNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
