package com.project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @ManyToOne(targetEntity = Movie.class)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    Movie movie;

    @ManyToOne()
    Theater theater;
    LocalDateTime startTime;
    LocalDateTime endTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Seat> seats;

}
