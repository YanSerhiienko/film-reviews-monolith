package com.seyan.reviewmonolith.review.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReviewCreationDTO(
        //@NotNull
        Double rating,
        //@NotNull
        Boolean isLiked,
        //@NotNull
        String content,
        Boolean containsSpoilers,

        //todo fix format
        //@JsonFormat(pattern =
        // "yyyy-MM-dd")
        //@NotNull
        @NotNull
        Long filmId,
        @NotNull
        Long userId,

        LocalDate watchedOnDate,
        Boolean watchedThisFilmBefore


) {

}
