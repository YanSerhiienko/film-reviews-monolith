package com.seyan.reviewmonolith.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
@DynamicUpdate
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Double rating;
    //private Boolean isLikedFilm;
    private String content;
    //todo this field adds film to your diary
    private LocalDate watchedOnDate;
    private Long filmId;
    private Long authorId;
    //private Long reviewLikeCount;
    private List<Long> likedUsersIds;
    //private Long commentCount;
    private List<Long> commentIds;
    //todo counts only if has content
    private Boolean containsSpoilers;
    private Boolean watchedThisFilmBefore;

    //TODO boolean flags + user service get methods
}
