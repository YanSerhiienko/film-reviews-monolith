package com.seyan.reviewmonolith.review;

import com.seyan.reviewmonolith.exception.review.ReviewNotFoundException;
import com.seyan.reviewmonolith.activity.dto.ActivityAndReviewCreationDTO;
import com.seyan.reviewmonolith.review.dto.ReviewCreationDTO;
import com.seyan.reviewmonolith.review.dto.ReviewMapper;
import com.seyan.reviewmonolith.review.dto.ReviewUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public Review createReview(ReviewCreationDTO dto) {
        Review review = reviewMapper.mapReviewCreationDTOToReview(dto);
        return reviewRepository.save(review);
    }

    public Review createReview(ActivityAndReviewCreationDTO request) {
        Review review = reviewMapper.mapActivityReviewDiaryRequestToReview(request);
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(
                String.format("No review found with the provided ID: %s", id)
        ));
    }

    //todo add sorting by your reviews and your network reviews
    public List<Review> getAllReviewsByFilmId(Long filmId) {
        return reviewRepository.findByFilmIdAndContentNotNull(filmId).stream()
                .sorted(Comparator.comparing(Review::getCreationDate).reversed())
                .toList();
    }

    public List<Review> getAllReviewsByUserId(Long userId) {
        return reviewRepository.findByUserIdAndContentNotNull(userId).stream()
                .sorted(Comparator.comparing(Review::getCreationDate).reversed())
                .toList();
    }

    //todo controller method
    public List<Review> getAllReviewsByUserIdAsDiary(Long userId) {
        return reviewRepository.findByUserIdAndWatchedOnDateNotNull(userId).stream()
                .sorted(Comparator.comparing(Review::getCreationDate).reversed())
                .toList();
    }

    //todo get by /username/film/film-title

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    //todo rework since liked films are based on activity
    public Review updateReview(Long reviewId, ReviewUpdateDTO dto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(
                String.format("Cannot update review:: No review found with the provided ID: %s", reviewId)
        ));

        Review mapped = reviewMapper.mapReviewUpdateDTOToReview(dto, review);
        return reviewRepository.save(mapped);
    }

    public void deleteReview(Long id) {
        reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(
                String.format("Cannot delete review:: No review found with the provided ID: %s", id)));

        reviewRepository.deleteById(id);
    }

    public int countUserReviewsForFilm(Long userId, Long filmId) {
        return reviewRepository.countByUserIdAndFilmId(userId, filmId);
    }

    public List<Long> getFilmIdBasedOnReviewDateAfter(LocalDate date) {
        return reviewRepository.findFilmIdBasedOnReviewCreationDateAfter(date);
    }

    public List<Long> getFilmIdBasedOnReviewDateBefore(LocalDate date) {
        return reviewRepository.findFilmIdBasedOnReviewCreationDateBefore(date);
    }

    public List<Long> getAllFilmIds() {
        return reviewRepository.findAllFilmIds();
    }
}
