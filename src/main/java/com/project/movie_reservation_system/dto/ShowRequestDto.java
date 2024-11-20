package com.project.movie_reservation_system.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowRequestDto {

    private long movieId;
    private long theaterId;
    private String startTime;
    private String endTime;
    private List<SeatStructure> seats;
}


