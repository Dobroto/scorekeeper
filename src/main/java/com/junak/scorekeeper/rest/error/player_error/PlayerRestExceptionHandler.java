package com.junak.scorekeeper.rest.error.player_error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerRestExceptionHandler {
    // Add an exception handler for CustomerNotFoundException

    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> handleException(PlayerNotFoundException exc) {

        // create CustomerErrorResponse

        PlayerErrorResponse error = new PlayerErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> handleException(Exception exc) {

        // create CustomerErrorResponse

        PlayerErrorResponse error = new PlayerErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
