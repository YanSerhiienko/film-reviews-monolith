package com.seyan.reviewmonolith.log;

import com.seyan.reviewmonolith.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
@DynamicUpdate
public class ActivityOnFilm {
    /*@Id
    private Long userId;
    @Id
    private Long filmId;*/
    @EmbeddedId
    private ActivityOnFilmId id;
    private Boolean isWatched;
    private Boolean isLiked;
    private Boolean isInWatchlist;
    private Double rating;
    //private Boolean hasReview;
    //private Long lastReviewId;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Review> filmReviews;

    //todo relation
    //@OneToOne
    //private Review review;
}
