package com.project.movie_reservation_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {

    String token;
}
