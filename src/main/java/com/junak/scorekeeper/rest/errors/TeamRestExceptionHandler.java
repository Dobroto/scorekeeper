package com.junak.scorekeeper.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TeamRestExceptionHandler {
    // Add an exception handler for CustomerNotFoundException

    @ExceptionHandler
    public ResponseEntity<TeamErrorResponse> handleException(TeamNotFoundException exc) {

        // create CustomerErrorResponse

        TeamErrorResponse error = new TeamErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<TeamErrorResponse> handleException(Exception exc) {

        // create CustomerErrorResponse

        TeamErrorResponse error = new TeamErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
