package com.seyan.reviewmonolith.log.dto;

import com.seyan.reviewmonolith.log.ActivityOnFilmId;

public record ActivityOnFilmResponse(
        ActivityOnFilmId id,
        Double rating,
        Boolean isWatched,
        Boolean isLiked,
        Boolean isInWatchlist,
        Boolean hasReview
) {

}
