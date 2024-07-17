package com.seyan.reviewmonolith.review;

import com.seyan.reviewmonolith.exception.review.ReviewNotFoundException;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.log.dto.ActivityReviewDiaryRequest;
import com.seyan.reviewmonolith.review.dto.ReviewCreationDTO;
import com.seyan.reviewmonolith.review.dto.ReviewMapper;
import com.seyan.reviewmonolith.review.dto.ReviewUpdateDTO;
import com.seyan.reviewmonolith.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final FilmService filmService;
    private final UserService userService;

    /*public Review createReview(ReviewCreationDTO dto) {
        Review review = reviewMapper.mapReviewCreationDTOToReview(dto);
        //todo fix date format
        review.setWatchedOnDate(LocalDate.now());

        if (dto.isLikedFilm()) {
            //todo change boolean to int (???)
            filmService.updateLikeCount(true);
            userService.addFilmToLiked(review.getAuthorId(), review.getFilmId());

        }

        filmService.updateWatchedCount(1);
        userService.addFilmToWatched(review.getAuthorId(), review.getFilmId());
        return reviewRepository.save(review);
    }*/

    //todo add flag isHasReview to activity entity
    public Review createReview(ReviewCreationDTO dto) {
        Review review = reviewMapper.mapReviewCreationDTOToReview(dto);

        //todo is film automatically added to watched?
        //userService.addFilmToWatched(review.getAuthorId(), review.getFilmId());
        return reviewRepository.save(review);
    }

    public Review createReview(ActivityReviewDiaryRequest request) {
        Review review = reviewMapper.mapActivityReviewDiaryRequestToReview(request);

        //todo is film automatically added to watched?
        //userService.addFilmToWatched(review.getAuthorId(), review.getFilmId());
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(
                String.format("No review found with the provided ID: %s", id)
        ));
    }

    //todo get by /username/film/film-title

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    //todo rework since liked films are based on activity
    public Review updateReview(ReviewUpdateDTO dto) {
        Review review = reviewRepository.findById(dto.id()).orElseThrow(() -> new ReviewNotFoundException(
                String.format("No review found with the provided ID: %s", dto.id())
        ));

        if (dto.isLikedFilm() != review.getIsLiked()) {
            filmService.updateLikeCount(dto.isLikedFilm());
            if (dto.isLikedFilm()) {
                userService.addFilmToLiked(review.getAuthorId(), review.getFilmId());
            } else {
                userService.removeFilmFromLiked(review.getAuthorId(), review.getFilmId());
            }
        }

        Review mapped = reviewMapper.mapReviewUpdateDTOToReview(dto, review);
        return reviewRepository.save(mapped);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(
                String.format("Cannot delete review:: No review found with the provided ID: %s", id)));

        if (review.getIsLiked()) {
            filmService.updateLikeCount(false);
            userService.removeFilmFromLiked(review.getAuthorId(), review.getFilmId());
        }

        reviewRepository.deleteById(id);
    }

    public List<Review> getPopularReviews(String popularity) {
        return null;
    }

    public List<Long> getFilmIdFromPopularReviews(String popularity) {
        //popularity = week month year
        //get reviews for last week by creation date, sort by film
        //select film, creationDate from reviews where creationDate < :date

        return null;
    }

    public List<Long> getFilmIdFromReviewsWithFilmRatingByUserId(Long userId) {
        return null;
    }
}
