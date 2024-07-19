package com.seyan.reviewmonolith.review.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReviewUpdateDTO(
        @NotNull
        Long id,
        Double rating,
        Boolean isLikedFilm,
        String content,
        Boolean containsSpoilers,
        LocalDate watchedOnDate,
        Boolean watchedThisFilmBefore
) {

}
