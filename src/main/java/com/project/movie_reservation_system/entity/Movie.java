package com.project.movie_reservation_system.entity;

import com.project.movie_reservation_system.enums.MovieGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    String movieName;

    @Enumerated(value = EnumType.STRING)
    List<MovieGenre> genre;
    int movieLength;
    String movieLanguage;
    LocalDate releaseDate;

}
