package com.project.movie_reservation_system.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponseDto {

    String message;
    Object data;
}
