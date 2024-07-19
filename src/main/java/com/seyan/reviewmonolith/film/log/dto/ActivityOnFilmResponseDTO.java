package com.seyan.reviewmonolith.film.log.dto;

import com.seyan.reviewmonolith.film.log.ActivityOnFilmId;

import java.time.LocalDate;

public record ActivityOnFilmResponseDTO(
        ActivityOnFilmId id,
        Boolean isWatched,
        Boolean isLiked,
        Boolean isInWatchlist,
        Double rating,
        LocalDate watchlistAddDate
        //Boolean hasReview
) {

}
