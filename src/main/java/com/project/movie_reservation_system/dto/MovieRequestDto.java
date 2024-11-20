package com.project.movie_reservation_system.dto;

import com.project.movie_reservation_system.enums.MovieGenre;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MovieRequestDto {

    String movieName;
    List<String> genre;
    int movieLength;
    String movieLanguage;
    String releaseDate;

}
