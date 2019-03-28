package com.junak.scorekeeper.rest.error.player_hitting_details_error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerHittingDetailsRestExceptionHandler {
    // Add an exception handler for PlayerHittingDetailsNotFoundException

    @ExceptionHandler
    public ResponseEntity<PlayerHittingDetailsErrorResponse> handleException(PlayerHittingDetailsNotFoundException exc) {

        // create PlayerHittingDetailsErrorResponse

        PlayerHittingDetailsErrorResponse error = new PlayerHittingDetailsErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<PlayerHittingDetailsErrorResponse> handleException(Exception exc) {

        // create CustomerErrorResponse

        PlayerHittingDetailsErrorResponse error = new PlayerHittingDetailsErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
