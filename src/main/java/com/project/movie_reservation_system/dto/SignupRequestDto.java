package com.project.movie_reservation_system.dto;

import com.project.movie_reservation_system.enums.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignupRequestDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
