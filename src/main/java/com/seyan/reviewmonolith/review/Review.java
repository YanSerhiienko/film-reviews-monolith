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
    private Long filmId;
    private Long authorId;
    private String content;
    //todo has minutes
    private LocalDate creationDate;
    private Boolean isLiked;
    private Double rating;

    //todo this fields adds film to your diary
    //private LocalDate watchedOnDate;
    //private Boolean watchedThisFilmBefore;


    //private Long reviewLikeCount;
    private List<Long> likedUsersIds;
    //private Long commentCount;
    private List<Long> commentIds;
    //todo counts only if has content
    private Boolean containsSpoilers;

    //TODO boolean flags + user service get methods
}
