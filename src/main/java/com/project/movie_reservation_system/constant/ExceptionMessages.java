package com.project.movie_reservation_system.constant;

public interface ExceptionMessages {
    String USER_EXISTS = "Username already taken";
    String USER_NOT_FOUND = "Username not found";
    String MOVIE_NOT_FOUND = "Movie not found";
    String THEATER_NOT_FOUND = "Theater not found";
    String SHOW_NOT_FOUND = "Show not found";
    String RESERVATION_NOT_FOUND = "Reservation not found";
    String AMOUNT_NOT_MATCH = "Paying amount is not matching with the amount to be paid";
    String SEAT_ALREADY_BOOKED = "Seats are already booked";
    String SEAT_LOCK_ACQUIRED = "Seat is getting booked by another user";
    String SHOW_STARTED_EXCEPTION = "Show is already started";
}
