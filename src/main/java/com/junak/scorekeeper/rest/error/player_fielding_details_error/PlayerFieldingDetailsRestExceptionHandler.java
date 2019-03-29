package com.junak.scorekeeper.rest.error.player_fielding_details_error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class PlayerFieldingDetailsRestExceptionHandler {
    // Add an exception handler for CustomerNotFoundException

    @ExceptionHandler
    public ResponseEntity<PlayerFieldingDetailsErrorResponse> handleException(PlayerFieldingDetailsNotFoundException exc) {

        // create CustomerErrorResponse

        PlayerFieldingDetailsErrorResponse error = new PlayerFieldingDetailsErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<PlayerFieldingDetailsErrorResponse> handleException(Exception exc) {

        // create CustomerErrorResponse

        PlayerFieldingDetailsErrorResponse error = new PlayerFieldingDetailsErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
