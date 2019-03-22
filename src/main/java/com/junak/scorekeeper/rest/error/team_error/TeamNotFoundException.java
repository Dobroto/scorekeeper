package com.junak.scorekeeper.rest.error.team_error;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {

    }

    public TeamNotFoundException (String mesage){
        super(mesage);
    }

    public TeamNotFoundException (Throwable cause){
        super(cause);
    }

    public TeamNotFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public TeamNotFoundException (String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
