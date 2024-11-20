package com.project.movie_reservation_system.exception;

import org.springframework.http.HttpStatus;

public class TheaterNotFoundException extends CustomException {
    public TheaterNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
