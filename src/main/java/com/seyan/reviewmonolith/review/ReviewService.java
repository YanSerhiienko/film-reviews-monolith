package com.seyan.reviewmonolith.review;

import com.seyan.reviewmonolith.exception.review.ReviewNotFoundException;
import com.seyan.reviewmonolith.review.dto.ReviewCreationDTO;
import com.seyan.reviewmonolith.review.dto.ReviewMapper;
import com.seyan.reviewmonolith.review.dto.ReviewUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(
                String.format("No review found with the provided ID: %s", id)
        ));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(ReviewUpdateDTO dto) {
        Review review = reviewRepository.findById(dto.id()).orElseThrow(() -> new ReviewNotFoundException(
                String.format("No review found with the provided ID: %s", dto.id())
        ));
        Review mapped = reviewMapper.mapReviewUpdateDTOToReview(dto, review);
        return reviewRepository.save(mapped);
    }

    public void deleteReview(Long id) {
        reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(
                String.format("Cannot delete review:: No review found with the provided ID: %s", id)));
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
