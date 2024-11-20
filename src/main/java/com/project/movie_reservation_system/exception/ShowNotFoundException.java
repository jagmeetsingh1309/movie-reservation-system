package com.project.movie_reservation_system.exception;

import org.springframework.http.HttpStatus;

public class ShowNotFoundException extends CustomException{

    public ShowNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
