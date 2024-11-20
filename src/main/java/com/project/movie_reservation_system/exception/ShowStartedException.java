package com.project.movie_reservation_system.exception;

import org.springframework.http.HttpStatus;

public class ShowStartedException extends CustomException{

    public ShowStartedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
