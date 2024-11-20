package com.project.movie_reservation_system.controller;

import com.project.movie_reservation_system.dto.ApiResponseDto;
import com.project.movie_reservation_system.dto.MovieRequestDto;
import com.project.movie_reservation_system.dto.PagedApiResponseDto;
import com.project.movie_reservation_system.entity.Movie;
import com.project.movie_reservation_system.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public ResponseEntity<PagedApiResponseDto> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Page<Movie> moviePage = movieService.getAllMovies(page, pageSize);
        List<Movie> movies = moviePage.getContent();
        return ResponseEntity.ok(
            PagedApiResponseDto.builder()
                        .totalPages(moviePage.getTotalPages())
                        .totalElements(moviePage.getTotalElements())
                        .currentPageData(movies)
                        .build()
        );
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponseDto> getMovieById(@PathVariable long movieId){
        Movie movie = movieService.getMovieById(movieId);
        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .message("Fetched movie with id: " + movieId)
                        .data(movie)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createNewMovie(@RequestBody MovieRequestDto movieRequestDto){
        Movie movie = movieService.createNewMovie(movieRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseDto.builder()
                                .message("Movie created")
                                .data(movie)
                                .build()
                );
    }

    @PutMapping("/movie/update/{movieId}")
    public ResponseEntity<ApiResponseDto> updateMovieById(@PathVariable long movieId, @RequestBody MovieRequestDto movieRequestDto){
        Movie movie = movieService.updateMovieById(movieId, movieRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseDto.builder()
                                .message("Movie updated")
                                .data(movie)
                                .build()
                );
    }

    @DeleteMapping("/movie/delete/{movieId}")
    public ResponseEntity<?> deleteMovieById(@PathVariable long movieId){
        movieService.deleteMovieById(movieId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // User should be able to filter movies by genres and languages


}
