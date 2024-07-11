package com.seyan.reviewmonolith.review.dto;

import java.time.LocalDate;
import java.util.List;

public record ReviewResponseDTO(
        Long id,
        Double rating,
        Boolean isLikedFilm,
        String content,
        LocalDate creationDate,
        Long filmId,
        Long authorId,
        Long reviewLikeCount,
        Long commentCount,
        List<Long> commentIds,
        Boolean containsSpoilers,
        Boolean watchedThisFilmBefore
) {

}
