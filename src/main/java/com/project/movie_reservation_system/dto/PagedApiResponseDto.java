package com.project.movie_reservation_system.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagedApiResponseDto {
    int totalPages;
    long totalElements;
    List<?> currentPageData;
    int currentCount;
}
