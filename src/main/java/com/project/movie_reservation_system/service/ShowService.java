package com.project.movie_reservation_system.service;

import com.project.movie_reservation_system.dto.ShowRequestDto;
import com.project.movie_reservation_system.entity.Seat;
import com.project.movie_reservation_system.entity.Show;
import com.project.movie_reservation_system.exception.MovieNotFoundException;
import com.project.movie_reservation_system.exception.ShowNotFoundException;
import com.project.movie_reservation_system.exception.TheaterNotFoundException;
import com.project.movie_reservation_system.repository.MovieRepository;
import com.project.movie_reservation_system.repository.ShowRepository;
import com.project.movie_reservation_system.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.movie_reservation_system.constant.ExceptionMessages.*;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final SeatService seatService;

    @Autowired
    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, TheaterRepository theaterRepository, SeatService seatService) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.seatService = seatService;
    }

    public Show createNewShow(ShowRequestDto showRequestDto) {
        return movieRepository.findById(showRequestDto.getMovieId())
                .map(movie -> theaterRepository.findById(showRequestDto.getTheaterId())
                        .map(theater -> {
                            List<Seat> seats = new ArrayList<>();
                            showRequestDto.getSeats()
                                    .forEach(seatStructure ->
                                            seats.addAll(
                                                    seatService.createSeatsWithGivenPrice(
                                                            seatStructure.getSeatCount(),
                                                            seatStructure.getSeatPrice(),
                                                            seatStructure.getArea()
                                                    )
                                            )
                                    );

                            Show show = Show.builder()
                                    .movie(movie)
                                    .theater(theater)
                                    .startTime(LocalDateTime.parse(showRequestDto.getStartTime()))
                                    .endTime(LocalDateTime.parse(showRequestDto.getEndTime()))
                                    .seats(seats)
                                    .build();
                            return showRepository.save(show);
                        })
                        .orElseThrow(() -> new TheaterNotFoundException(THEATER_NOT_FOUND, HttpStatus.BAD_REQUEST)))
                .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Page<Show> getllShows(int page, int size) {
        return showRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteShowById(long showId) {
        showRepository.deleteById(showId);
    }

    public Page<Show> filterShowsByTheaterIdAndMovieId(Long theaterId, Long movieId, PageRequest pageRequest) {
        if(theaterId == null && movieId == null){
            return showRepository.findAll(pageRequest);
        } else if(theaterId == null){
            return showRepository.findByMovieId(movieId, pageRequest);
        }
        return showRepository.findByTheaterIdAndMovieId(theaterId, movieId, pageRequest);
    }

    public Show getShowById(long showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFoundException(SHOW_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
