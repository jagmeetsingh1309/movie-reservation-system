package com.project.movie_reservation_system.repository;

import com.project.movie_reservation_system.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    Page<Theater> findAllByLocation(String location, Pageable pageable);
}
