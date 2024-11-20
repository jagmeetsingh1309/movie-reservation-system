package com.project.movie_reservation_system.controller;

import com.project.movie_reservation_system.dto.ApiResponseDto;
import com.project.movie_reservation_system.dto.PagedApiResponseDto;
import com.project.movie_reservation_system.dto.TheaterRequestDto;
import com.project.movie_reservation_system.entity.Theater;
import com.project.movie_reservation_system.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/all")
    public ResponseEntity<PagedApiResponseDto> getAllTheaters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Theater> theaterPage = theaterService.getAllTheaters(page, size);
        return ResponseEntity.ok(
            PagedApiResponseDto.builder()
                    .totalPages(theaterPage.getTotalPages())
                    .totalElements(theaterPage.getTotalElements())
                    .currentCount(theaterPage.getNumberOfElements())
                    .currentPageData(theaterPage.getContent())
                    .build()
        );
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<PagedApiResponseDto> getAllTheatersByLocation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String location
    ){
        Page<Theater> theaterPage = theaterService.getAllTheatersByLocation(page, size, location);
        return ResponseEntity.ok(
                PagedApiResponseDto.builder()
                        .totalPages(theaterPage.getTotalPages())
                        .totalElements(theaterPage.getTotalElements())
                        .currentCount(theaterPage.getNumberOfElements())
                        .currentPageData(theaterPage.getContent())
                        .build()
        );
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<ApiResponseDto> getTheaterById(@PathVariable long theaterId){
        Theater theater = theaterService.getTheaterById(theaterId);
        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .data(theater)
                        .message("Fetched theater by id: " + theater.getId())
                        .build()
        );
    }


    @PostMapping("/theater/create")
    public ResponseEntity<ApiResponseDto> createTheater(@RequestBody TheaterRequestDto theaterRequestDto){
        Theater theater = theaterService.createNewTheater(theaterRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseDto.builder()
                                .message("New Theater created with id: " + theater.getId())
                                .data(theater)
                                .build()
                );
    }

    @PutMapping("/theater/update/{theaterId}")
    public ResponseEntity<ApiResponseDto> updateTheaterById(@PathVariable long theaterId, @RequestBody TheaterRequestDto theaterRequestDto){
        Theater updatedTheater = theaterService.updateTheaterById(theaterId, theaterRequestDto);
        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .message("Theater updated")
                        .data(updatedTheater)
                        .build()
        );
    }


    @DeleteMapping("/theater/delete/{theaterId}")
    public ResponseEntity<?> deleteTheaterById(@PathVariable long theaterId){
        theaterService.deleteTheaterById(theaterId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
