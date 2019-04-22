package com.junak.scorekeeper.rest.error.game_hitting_details_error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameHittingDetailsRestExceptionHandler {
    // Add an exception handler for GameHittingDetailsNotFoundException

    @ExceptionHandler
    public ResponseEntity<GameHittingDetailsErrorResponse> handleException(GameHittingDetailsNotFoundException exc) {

        // create GameHittingDetailsErrorResponse

        GameHittingDetailsErrorResponse error = new GameHittingDetailsErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<GameHittingDetailsErrorResponse> handleException(Exception exc) {

        GameHittingDetailsErrorResponse error = new GameHittingDetailsErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
