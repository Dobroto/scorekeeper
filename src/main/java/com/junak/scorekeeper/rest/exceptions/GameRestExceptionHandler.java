package com.junak.scorekeeper.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GameRestExceptionHandler {
    // Add an exception handler for GameNotFoundException

    @ExceptionHandler
    public ResponseEntity<GameErrorResponse> handleException(GameNotFoundException exc) {

        // create GameErrorResponse
        exc.printStackTrace();
        GameErrorResponse error = new GameErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<GameErrorResponse> handleException(Exception exc) {

        // create GameErrorResponse

        GameErrorResponse error = new GameErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
