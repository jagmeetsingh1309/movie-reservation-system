package com.project.movie_reservation_system.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends CustomException{

    public ReservationNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
