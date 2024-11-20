package com.project.movie_reservation_system.controller;

import com.project.movie_reservation_system.dto.ApiResponseDto;
import com.project.movie_reservation_system.dto.PagedApiResponseDto;
import com.project.movie_reservation_system.dto.ShowRequestDto;
import com.project.movie_reservation_system.entity.Show;
import com.project.movie_reservation_system.service.ShowService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }


    @GetMapping("/all")
    public ResponseEntity<PagedApiResponseDto> getAllShows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Show> showPage = showService.getllShows(page, size);
        return ResponseEntity.ok(
                PagedApiResponseDto.builder()
                        .currentCount(showPage.getNumberOfElements())
                        .currentPageData(showPage.getContent())
                        .totalElements(showPage.getTotalElements())
                        .totalPages(showPage.getTotalPages())
                        .build()
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<PagedApiResponseDto> filterShows(
            @RequestParam(required = false) Long theaterId,
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) String showDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Show> showPage = showService.filterShowsByTheaterIdAndMovieId(theaterId, movieId, PageRequest.of(page, size));
        return ResponseEntity.ok(
                PagedApiResponseDto.builder()
                        .currentCount(showPage.getNumberOfElements())
                        .currentPageData(showPage.getContent())
                        .totalElements(showPage.getTotalElements())
                        .totalPages(showPage.getTotalPages())
                        .build()
        );
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<ApiResponseDto> getShowById(@PathVariable long showId){
        Show show = showService.getShowById(showId);
        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .data(show)
                        .message("Fetched show with id: " + show.getId())
                        .build()
        );
    }

    @PostMapping("/show/create")
    public ResponseEntity<ApiResponseDto> createShow(@RequestBody ShowRequestDto showRequestDto){
        Show show = showService.createNewShow(showRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseDto.builder()
                                .message("Show created with id: " + show.getId())
                                .data(show)
                                .build()
                );
    }

    @PatchMapping("/show/update/movie/{showId}")
    public ResponseEntity<ApiResponseDto> updateMovie(){
        return null;
    }

    @PatchMapping("/show/update/theatre/{showId}")
    public ResponseEntity<ApiResponseDto> updateTheatre(){
        return null;
    }

    @PatchMapping("/show/update/timings/{showId}")
    public ResponseEntity<ApiResponseDto> updateShowTimings(){
        return null;
    }

    @DeleteMapping("/show/delete/{showId}")
    public ResponseEntity<?> deleteShowById(@PathVariable long showId){
        showService.deleteShowById(showId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
