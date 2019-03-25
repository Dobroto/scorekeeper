package com.junak.scorekeeper.rest.error.player_pitching_details_error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerPitchingDetailsRestExceptionHandler {
    // Add an exception handler for CustomerNotFoundException

    @ExceptionHandler
    public ResponseEntity<PlayerPitchingDetailsErrorResponse> handleException(PlayerPitchingDetailsNotFoundException exc) {

        // create CustomerErrorResponse

        PlayerPitchingDetailsErrorResponse error = new PlayerPitchingDetailsErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<PlayerPitchingDetailsErrorResponse> handleException(Exception exc) {

        // create CustomerErrorResponse

        PlayerPitchingDetailsErrorResponse error = new PlayerPitchingDetailsErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
