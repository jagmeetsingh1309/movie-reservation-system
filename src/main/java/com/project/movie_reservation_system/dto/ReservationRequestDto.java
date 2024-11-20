package com.project.movie_reservation_system.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservationRequestDto {

    private long showId;
    private List<Long> seatIdsToReserve;
    private double amount;

}
