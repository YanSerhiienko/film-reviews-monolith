package com.seyan.reviewmonolith.review.dto;

import java.time.LocalDate;
import java.util.List;

public record ReviewResponseDTO(
        Long id,
        //Double rating,
        //Boolean isLikedFilm,
        String content,
        LocalDate watchedOnDate,
        Long filmId,
        Long authorId,
        //Long reviewLikeCount,
        List<Long> likedUsersIds,
        //Long commentCount,
        List<Long> commentIds,
        Boolean containsSpoilers,
        Boolean watchedThisFilmBefore
) {

}
