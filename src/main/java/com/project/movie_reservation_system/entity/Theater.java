package com.project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    String name;
    String location;

}
