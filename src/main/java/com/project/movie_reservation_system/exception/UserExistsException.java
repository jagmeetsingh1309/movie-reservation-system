package com.project.movie_reservation_system.exception;

import org.springframework.http.HttpStatus;

public class UserExistsException extends CustomException{

    public UserExistsException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
