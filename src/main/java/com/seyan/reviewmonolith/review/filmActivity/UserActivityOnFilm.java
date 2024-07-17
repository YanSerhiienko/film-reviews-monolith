package com.seyan.reviewmonolith.review.filmActivity;

import com.seyan.reviewmonolith.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
@DynamicUpdate
public class UserActivityOnFilm {
    /*@Id
    private Long userId;
    @Id
    private Long filmId;*/
    @EmbeddedId
    private UserActivityOnFilmId id;
    private Double rating;
    private Boolean isWatched;
    private Boolean isLiked;
    private Boolean isInWatchlist;
    private Boolean hasReview;

    //todo relation
    //@OneToOne
    //private Review review;
}
