package com.project.movie_reservation_system.dto;

import lombok.Data;

@Data
public class SeatStructure {
    private int seatCount;
    private double seatPrice;
    private String area;
}
