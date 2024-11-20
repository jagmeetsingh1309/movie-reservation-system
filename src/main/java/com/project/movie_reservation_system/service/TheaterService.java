package com.project.movie_reservation_system.service;

import com.project.movie_reservation_system.dto.TheaterRequestDto;
import com.project.movie_reservation_system.entity.Theater;
import com.project.movie_reservation_system.exception.TheaterNotFoundException;
import com.project.movie_reservation_system.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.project.movie_reservation_system.constant.ExceptionMessages.THEATER_NOT_FOUND;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public Theater createNewTheater(TheaterRequestDto theaterRequestDto) {
        Theater theater = Theater.builder()
                .name(theaterRequestDto.getName())
                .location(theaterRequestDto.getLocation())
                .build();
        return theaterRepository.save(theater);
    }

    public Page<Theater> getAllTheaters(int page, int size) {
        return theaterRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Theater> getAllTheatersByLocation(int page, int size, String location) {
        return theaterRepository.findAllByLocation(location, PageRequest.of(page, size));
    }

    public Theater getTheaterById(long theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new TheaterNotFoundException(THEATER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public void deleteTheaterById(long theaterId) {
        theaterRepository.deleteById(theaterId);
    }

    public Theater updateTheaterById(long theaterId, TheaterRequestDto theaterRequestDto) {
        return theaterRepository.findById(theaterId)
                .map(theater -> {
                    theater.setName(theaterRequestDto.getName());
                    theater.setLocation(theater.getLocation());
                    return theaterRepository.save(theater);
                })
                .orElseThrow(() -> new TheaterNotFoundException(THEATER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
