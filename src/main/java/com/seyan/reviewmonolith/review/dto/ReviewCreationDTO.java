package com.seyan.reviewmonolith.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReviewCreationDTO(
        //@NotNull
        //Double rating,
        //@NotNull
        //Boolean isLikedFilm,
        @NotNull
        String content,
        //todo fix format
        @JsonFormat(pattern = "yyyy-MM-dd")
        //@NotNull
        LocalDate watchedOnDate,
        @NotNull
        Long filmId,
        @NotNull
        Long authorId,
        Boolean containsSpoilers,
        Boolean watchedThisFilmBefore
) {

}
