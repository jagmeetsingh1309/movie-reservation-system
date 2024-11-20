package com.project.movie_reservation_system.exception;

import org.springframework.http.HttpStatus;

public class AmountNotMatchException extends CustomException{

    public AmountNotMatchException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
