package com.seyan.reviewmonolith.film.log.dto;

import com.seyan.reviewmonolith.film.log.ActivityOnFilmId;

public record ActivityOnFilmResponse(
        ActivityOnFilmId id,
        Boolean isWatched,
        Boolean isLiked,
        Boolean isInWatchlist,
        Double rating
        //Boolean hasReview
) {

}
