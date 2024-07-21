package com.seyan.reviewmonolith.activity.dto;

import com.seyan.reviewmonolith.activity.ActivityOnFilmId;

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
