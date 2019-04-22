package com.junak.scorekeeper.rest.error.game_pitching_details_error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GamePitchingDetailsRestExceptionHandler {
    // Add an exception handler for GamePitchingDetailsNotFoundException

    @ExceptionHandler
    public ResponseEntity<GamePitchingDetailsErrorResponse> handleException(GamePitchingDetailsNotFoundException exc) {

        // create GamePitchingDetailsErrorResponse

        GamePitchingDetailsErrorResponse error = new GamePitchingDetailsErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<GamePitchingDetailsErrorResponse> handleException(Exception exc) {

        // create GamePitchingDetailsErrorResponse

        GamePitchingDetailsErrorResponse error = new GamePitchingDetailsErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
