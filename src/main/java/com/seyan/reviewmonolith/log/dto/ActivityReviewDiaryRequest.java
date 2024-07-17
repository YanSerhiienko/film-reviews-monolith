package com.seyan.reviewmonolith.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seyan.reviewmonolith.log.ActivityOnFilmId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ActivityReviewDiaryRequest(
        @NotNull
        Long filmId,
        @NotNull
        Long userId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate watchedOnDate,
        Boolean watchedThisFilmBefore,
        String reviewContent,
        @Max(value = 5)
        Double rating,
        Boolean isLiked,
        Boolean containsSpoilers
) {

}
