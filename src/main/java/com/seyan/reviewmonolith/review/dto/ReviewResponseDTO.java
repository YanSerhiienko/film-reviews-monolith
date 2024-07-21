package com.seyan.reviewmonolith.review.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record ReviewResponseDTO(
        Long id,
        Double rating,
        Boolean isLiked,
        String content,
        LocalDate watchedOnDate,
        Boolean containsSpoilers,
        LocalDate creationDate,
        Long filmId,
        Long authorId,
        //Long reviewLikeCount,
        Set<Long> likedUsersIds,
        //Long commentCount,
        Set<Long> commentIds
        //Boolean watchedThisFilmBefore
) {

}
